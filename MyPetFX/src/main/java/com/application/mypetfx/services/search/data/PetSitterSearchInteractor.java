package com.application.mypetfx.services.search.data;

import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;
import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import com.application.mypetfx.utils.exceptions.InvalidInputException;
import com.application.mypetfx.utils.input.InputValidator;
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

public class PetSitterSearchInteractor {

    private static final Logger logger = Logger.getLogger(PetSitterSearchInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private static final String ERROR = "Something went wrong...";
    private final DBProfile dbProfile;
    private final PetSitterSearchContract.PetSitterSearchListener petSitterSearchListener;

    public PetSitterSearchInteractor(PetSitterSearchContract.PetSitterSearchListener petSitterSearchListener) {
        this.petSitterSearchListener = petSitterSearchListener;
        dbProfile = new DBProfile();
    }

    public void findPetSitters(String user, PetSitSearchFilters petSitSearchFilters) {

        if (hasInputError(petSitSearchFilters)) {
            return;
        }

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> findResults(user, petSitSearchFilters));
                    }
                }, 1500);
    }

    private boolean hasInputError(PetSitSearchFilters petSitSearchFilters) {
        String region = petSitSearchFilters.getRegion();

        InputValidator validator = new InputValidator();
        try {
            if (validator.isEmpty(region)) {
                throw new InvalidInputException("Select a region.");
            }
            return false;
        } catch (InvalidInputException e) {
            this.petSitterSearchListener.onFindResultsFailed(e.getMessage());
            return true;
        }
    }

    private void findResults(String user, PetSitSearchFilters petSitSearchFilters) {

        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            PetSitterResultsSingletonClass petSitterResultsSingletonClass = loadResultsFromDB(user, petSitSearchFilters, connection);
            this.dbProfile.closeConnection(connection);

            if (petSitterResultsSingletonClass == null) {
                this.petSitterSearchListener.onFindResultsFailed(ERROR);
            } else {
                this.petSitterSearchListener.onFindResultsSuccess(petSitterResultsSingletonClass);
            }
        }
        catch (ConnectionFailedException e) {
            this.petSitterSearchListener.onFindResultsFailed(e.getMessage());
        }

    }

    private PetSitterResultsSingletonClass loadResultsFromDB(String user, PetSitSearchFilters petSitSearchFilters, Connection connection) {
        String region = petSitSearchFilters.getRegion();
        String province = petSitSearchFilters.getProvince();
        boolean dog = petSitSearchFilters.isDog();
        boolean cat = petSitSearchFilters.isCat();
        boolean otherPets = petSitSearchFilters.isOtherPets();

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
        String query = "{ call search_pet_sitter(?, ?, ?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the values for the IN parameters
            stmt.setBoolean(1, dog);
            stmt.setBoolean(2, cat);
            stmt.setBoolean(3, otherPets);
            stmt.setString(4, region);
            stmt.setString(5, province);
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

                favorites.add(recoverFavorite(user, rs.getString("Username"), connection));
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
            logger.error("IO Error: ", e);
        } finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return petSitterResultsSingletonClass;
    }

    private boolean recoverFavorite(String user, String petSitter, Connection connection) {
        CallableStatement stmt = null;
        boolean out = false;
        String query = "{ call recover_pet_sitter_favorite(?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the values for the IN parameters
            stmt.setString(1, user);
            stmt.setString(2, petSitter);
            // Registering the type of the OUT parameter
            stmt.registerOutParameter(3, Types.BOOLEAN);
            // Executing the CallableStatement
            stmt.execute();

            // Retrieving the value for role
            out = stmt.getBoolean(3);
        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        }
        finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return out;
    }

}
