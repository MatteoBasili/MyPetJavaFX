package com.application.mypetfx.services.profile.data;

import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoadFavPetSitInteractor {

    private static final Logger logger = Logger.getLogger(LoadFavPetSitInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private static final String ERROR = "Something went wrong...";
    private final DBProfile dbProfile;
    private final ProfileContract.LoadFavPetSitListener loadFavPetSitListener;

    public LoadFavPetSitInteractor(ProfileContract.LoadFavPetSitListener loadFavPetSitListener) {
        this.loadFavPetSitListener = loadFavPetSitListener;
        dbProfile = new DBProfile();
    }

    public void loadFavourites(String user) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> load(user));
                    }
                }, 250);
    }

    private void load (String user) {

        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            PetSitterResultsSingletonClass petSitterResultsSingletonClass = loadFavouritesFromDB(user, connection);
            this.dbProfile.closeConnection(connection);

            if (petSitterResultsSingletonClass == null) {
                this.loadFavPetSitListener.onLoadFavoritesFailed(ERROR);
            } else {
                this.loadFavPetSitListener.onLoadFavoritesSuccess(petSitterResultsSingletonClass);
            }
        }
        catch (ConnectionFailedException e) {
            this.loadFavPetSitListener.onLoadFavoritesFailed(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private PetSitterResultsSingletonClass loadFavouritesFromDB(String user, Connection connection) {
        Image photo;
        int likes;
        int dislikes;
        List<LoadPetSitProfileInfo> profiles = new ArrayList<>();
        List<String> petSitters = new ArrayList<>();
        List<String> regions = new ArrayList<>();
        List<String> provinces = new ArrayList<>();
        List<Boolean> favorites = new ArrayList<>();

        CallableStatement stmt = null;
        PetSitterResultsSingletonClass petSitterResultsSingletonClass = null;
        String query = "{ call load_pet_sit_favorites(?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the values for the IN parameter
            stmt.setString(1, user);
            // Executing the CallableStatement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                petSitters.add(rs.getString("Username"));
                regions.add(rs.getString("Region"));
                provinces.add(rs.getString("Province"));
                likes = rs.getInt("Likes");
                dislikes = rs.getInt("Dislikes");
                Blob blob = rs.getBlob("Photo");
                if (blob != null) {
                    InputStream is = blob.getBinaryStream();
                    photo = new Image(is);
                    is.close();
                } else {
                    photo = null;
                }

                LoadPetSitProfileInfo loadPetSitProfileInfo = new LoadPetSitProfileInfo();
                loadPetSitProfileInfo.setImage(photo);
                loadPetSitProfileInfo.setNumLikes(likes);
                loadPetSitProfileInfo.setNumDislikes(dislikes);
                profiles.add(loadPetSitProfileInfo);

                favorites.add(true);
            }

            petSitterResultsSingletonClass = PetSitterResultsSingletonClass.getSingletonInstance();
            petSitterResultsSingletonClass.setPetSitProfileInfos(profiles);
            petSitterResultsSingletonClass.setFavorites(favorites);
            petSitterResultsSingletonClass.setUsernames(petSitters);
            petSitterResultsSingletonClass.setProvinces(provinces);
            petSitterResultsSingletonClass.setRegions(regions);

        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return petSitterResultsSingletonClass;
    }

}
