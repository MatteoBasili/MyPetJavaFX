package com.application.mypetfx.services.search.data;

import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Timer;
import java.util.TimerTask;

public class FavoritesPetSitInteractor {

    private static final Logger logger = Logger.getLogger(FavoritesPetSitInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private static final String ERROR = "Something went wrong...";
    private final DBProfile dbProfile;
    private final PetSitterSearchContract.PetSitterFavoritesListener petSitterFavoritesListener;

    public FavoritesPetSitInteractor(PetSitterSearchContract.PetSitterFavoritesListener petSitterFavoritesListener) {
        this.petSitterFavoritesListener = petSitterFavoritesListener;
        this.dbProfile = new DBProfile();
    }

    public void setPetSitterToFavorites(String user, String petSitter) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> setFavorite(user, petSitter));
                    }
                }, 0);
    }

    private void setFavorite(String user, String petSitter) {
        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            Boolean out = setFavoriteToDB(user, petSitter, connection);
            this.dbProfile.closeConnection(connection);

            if (out == null) {
                this.petSitterFavoritesListener.onSetFavoriteFailed(ERROR);
            }

            this.petSitterFavoritesListener.onSetFavoriteSuccess();
        }
        catch (ConnectionFailedException e) {
            this.petSitterFavoritesListener.onSetFavoriteFailed(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean setFavoriteToDB(String user, String petSitter, Connection connection) {
        Boolean out = null;
        CallableStatement stmt = null;
        String query = "{ call set_favorite(?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the value for the IN parameters
            stmt.setString(1, user);
            stmt.setString(2, petSitter);
            // Registering the type of the OUT parameter
            stmt.registerOutParameter(3, Types.BOOLEAN);
            // Executing the CallableStatement
            stmt.execute();

            // Retrieving the OUT parameter
            out = stmt.getBoolean(3);

        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        } finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return out;
    }

}
