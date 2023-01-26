package com.application.mypetfx.services.search.data;

import com.application.mypetfx.registration.data.PetSitCaredPets;
import com.application.mypetfx.registration.data.PetSitServices;
import com.application.mypetfx.registration.data.ProfileUserData;
import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;
import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.utils.db.DBProfile;
import com.application.mypetfx.utils.exceptions.ConnectionFailedException;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class PetSitterDetailsInteractor {

    private static final Logger logger = Logger.getLogger(PetSitterDetailsInteractor.class);
    private static final String SQL_EXCEPTION = "SQL Error: ";
    private final DBProfile dbProfile;
    private final PetSitterSearchContract.PetSitterDetailsListener petSitterDetailsListener;

    public PetSitterDetailsInteractor(PetSitterSearchContract.PetSitterDetailsListener petSitterDetailsListener) {
        this.petSitterDetailsListener = petSitterDetailsListener;
        dbProfile = new DBProfile();
    }

    public void loadDetails(String user, String petSitter) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> load(user, petSitter));
                    }
                }, 250);
    }

    private void load(String user, String petSitter) {
        try {
            Connection connection = dbProfile.getConnection();
            if (connection == null) {
                throw new ConnectionFailedException();
            }

            PetSitterDetails petSitterDetails = loadFromDB(user, petSitter, connection);
            this.dbProfile.closeConnection(connection);

            if (petSitterDetails == null) {
                this.petSitterDetailsListener.onLoadDetailsFailed("Error in loading profile.");
            }

            this.petSitterDetailsListener.onLoadDetailsSuccess(petSitterDetails);
        }
        catch (ConnectionFailedException e) {
            this.petSitterDetailsListener.onLoadDetailsFailed(e.getMessage());
        }
    }

    private PetSitterDetails loadFromDB(String user, String petSitter, Connection connection) {
        PetSitterDetails petSitterDetails = null;

        PetSitCaredPets petSitCaredPets = new PetSitCaredPets();
        PetSitServices petSitServices = new PetSitServices();
        PetSitterRating petSitterRating = new PetSitterRating();
        ProfileUserData profileUserData = new ProfileUserData();
        LoadPetSitProfileInfo loadPetSitProfileInfo = new LoadPetSitProfileInfo();
        String email = null;

        CallableStatement stmt = null;
        String query = "{ call load_pet_sit_details(?, ?, ?, ?) }";
        try {
            // Preparing a CallableStatement to call a procedure
            stmt = connection.prepareCall(query);
            // Setting the value for the IN parameters
            stmt.setString(1, user);
            stmt.setString(2, petSitter);
            // Registering the type of the OUT parameters
            stmt.registerOutParameter(3, Types.BOOLEAN);
            stmt.registerOutParameter(4, Types.INTEGER);
            // Executing the CallableStatement
            ResultSet rs = stmt.executeQuery();

            // Retrieving the OUT parameters
            while (rs.next()) {

                // Take cared pets
                petSitCaredPets.setDog(rs.getBoolean("Dog"));
                petSitCaredPets.setCat(rs.getBoolean("Cat"));
                petSitCaredPets.setOtherPets(rs.getBoolean("OtherPets"));

                // Take services
                petSitServices.setServ1(rs.getBoolean("AtHome"));
                petSitServices.setServ2(rs.getBoolean("AtPetSitHome"));
                petSitServices.setServ3(rs.getBoolean("HomeVisit"));
                petSitServices.setServ4(rs.getBoolean("Walk"));
                petSitServices.setServ5(rs.getBoolean("ChangeOfLitter"));
                petSitServices.setDescription(rs.getString("Description"));

                // Take photo, likes and dislikes
                loadPetSitProfileInfo.setNumLikes(rs.getInt("Likes"));
                loadPetSitProfileInfo.setNumDislikes(rs.getInt("Dislikes"));
                Blob blob = rs.getBlob("Photo");
                Image photo;
                if (blob != null) {
                    InputStream is = blob.getBinaryStream();
                    photo = new Image(is);
                    is.close();
                } else {
                    photo = null;
                }
                loadPetSitProfileInfo.setImage(photo);

                // Take profile data
                profileUserData.setName(rs.getString("Name"));
                profileUserData.setSurname(rs.getString("Surname"));
                profileUserData.setProvince(rs.getString("Province"));
                profileUserData.setPhoneNumb(rs.getString("PhoneNumber"));

                // Take email
                email = rs.getString("Email");
            }

            // Take rating
            petSitterRating.setFavorite(stmt.getBoolean(3));
            petSitterRating.setRating(stmt.getInt(4));

            petSitterDetails = new PetSitterDetails();
            petSitterDetails.setPetSitterRating(petSitterRating);
            petSitterDetails.setPetSitCaredPets(petSitCaredPets);
            petSitterDetails.setPetSitServices(petSitServices);
            petSitterDetails.setLoadPetSitProfileInfo(loadPetSitProfileInfo);
            petSitterDetails.setProfileUserData(profileUserData);
            petSitterDetails.setEmail(email);

        }
        catch (SQLException e) {
            logger.error(SQL_EXCEPTION, e);
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        } finally {
            assert stmt != null;
            this.dbProfile.closeStatement(stmt);
        }

        return petSitterDetails;
    }

}
