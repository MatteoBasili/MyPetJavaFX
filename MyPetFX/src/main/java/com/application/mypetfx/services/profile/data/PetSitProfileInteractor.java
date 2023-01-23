package com.application.mypetfx.services.profile.data;

import com.application.mypetfx.registration.data.RegistrationInteractor;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class PetSitProfileInteractor {

    private static final Logger logger = Logger.getLogger(RegistrationInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private static final String ERROR = "Something went wrong...";
    private final DBProfile dbProfile;
    private final ProfileContract.PetSitProfileListener petSitProfileListener;

    public PetSitProfileInteractor(ProfileContract.PetSitProfileListener petSitProfileListener) {
        this.petSitProfileListener = petSitProfileListener;
        dbProfile = new DBProfile();
    }

    public void loadProfile(String user) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> load(user));
                    }
                }, 250);
    }

    public void savePhoto(String user, Image image) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> save(user, image));
                    }
                }, 500);
    }

    private void load(String user) {
        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            LoadPetSitProfileInfo loadPetSitProfileInfo = loadFromDB(user, connection);
            this.dbProfile.closeConnection(connection);

            if (loadPetSitProfileInfo == null) {
                this.petSitProfileListener.onLoadProfileFailed("Error in loading profile.");
            }

            this.petSitProfileListener.onLoadProfileSuccess(loadPetSitProfileInfo);
        }
        catch (ConnectionFailedException e) {
            this.petSitProfileListener.onLoadProfileFailed(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void save(String user, Image image) {
        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            Boolean out = saveToDB(user, image, connection);
            this.dbProfile.closeConnection(connection);

            if (out == null) {
                this.petSitProfileListener.onStorePhotoFailed(ERROR);
            } else if(!out) {
                this.petSitProfileListener.onStorePhotoFailed("Photo saving failed. Try again.");
            } else {
                this.petSitProfileListener.onStorePhotoSuccess();
            }
        }
        catch (ConnectionFailedException e) {
            this.petSitProfileListener.onLoadProfileFailed(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LoadPetSitProfileInfo loadFromDB(String user, Connection connection) {
        LoadPetSitProfileInfo loadPetSitProfileInfo = null;
        CallableStatement stmt = null;
        String query = "{ call load_profile(?, ?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the value for the IN parameter
            stmt.setString(1, user);
            // Registering the type of the OUT parameters
            stmt.registerOutParameter(2, Types.BLOB);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.registerOutParameter(4, Types.INTEGER);
            // Executing the CallableStatement
            stmt.execute();

            // Retrieving the OUT parameters
            loadPetSitProfileInfo = new LoadPetSitProfileInfo();

            loadPetSitProfileInfo.setNumLikes(stmt.getInt(3));
            loadPetSitProfileInfo.setNumDislikes(stmt.getInt(4));
            Blob blob = stmt.getBlob(2);
            Image image;
            if (blob != null) {
                InputStream is = blob.getBinaryStream();
                image = new Image(is);
                is.close();
            } else {
                image = null;
            }
            loadPetSitProfileInfo.setImage(image);
        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return loadPetSitProfileInfo;
    }

    private Boolean saveToDB(String user, Image image, Connection connection) throws IOException {
        Boolean out = null;
        byte[] res;
        if (image != null) {
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            res = s.toByteArray();
            s.close();
        } else {
            res = null;
        }
        CallableStatement stmt = null;
        String query = "{ call save_profile_photo(?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the value for the IN parameters
            stmt.setString(1, user);
            stmt.setBytes(2, res);
            // Registering the type of the OUT parameter
            stmt.registerOutParameter(3, Types.INTEGER);
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
