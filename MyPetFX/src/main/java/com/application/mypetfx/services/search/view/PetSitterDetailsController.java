package com.application.mypetfx.services.search.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.search.FavoritesPetSitPresenter;
import com.application.mypetfx.services.search.PetSitterDetailsPresenter;
import com.application.mypetfx.services.search.PetSitterRatingPresenter;
import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.services.search.data.PetSitterDetails;
import com.application.mypetfx.services.search.data.Rating;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import com.application.mypetfx.utils.singleton_examples.PetSitterSingletonClass;
import com.application.mypetfx.utils.singleton_examples.ProfileImageSingletonClass;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PetSitterDetailsController implements Initializable, PetSitterSearchContract.PetSitterDetailsView, PetSitterSearchContract.PetSitterFavoritesView,
        PetSitterSearchContract.PetSitterRatingView {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView back;
    @FXML
    private Label username;
    @FXML
    private Label name;
    @FXML
    private Label place;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label email;
    @FXML
    private TextArea petSitterDescription;
    @FXML
    private ImageView likeImage;
    @FXML
    private ImageView dislikeImage;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;
    @FXML
    private CheckBox dogBox;
    @FXML
    private CheckBox catBox;
    @FXML
    private CheckBox otherPetsBox;
    @FXML
    private CheckBox service1;
    @FXML
    private CheckBox service2;
    @FXML
    private CheckBox service3;
    @FXML
    private CheckBox service4;
    @FXML
    private CheckBox service5;
    @FXML
    private ImageView noFavoritesIcon;
    @FXML
    private ImageView favoritesIcon;
    @FXML
    private ImageView noLikeImage;
    @FXML
    private ImageView noDislikeImage;
    @FXML
    private AnchorPane pictureFrame;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView defaultProfileImage;
    @FXML
    private ProgressIndicator progressIndicator;

    private UserSingletonClass userSingletonClass;
    private ProfileImageSingletonClass profileImageSingletonClass;
    private PetSitterSingletonClass petSitterSingletonClass;
    private PetSitterDetailsPresenter petSitterDetailsPresenter;
    private FavoritesPetSitPresenter favoritesPetSitPresenter;
    private PetSitterRatingPresenter petSitterRatingPresenter;
    private PetSitterResultsSingletonClass petSitterResultsSingletonClass;
    private Rating ratingState;
    private int positionInList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        profileImageSingletonClass = ProfileImageSingletonClass.getSingletonInstance();
        petSitterSingletonClass = PetSitterSingletonClass.getSingletonInstance();
        petSitterResultsSingletonClass = PetSitterResultsSingletonClass.getSingletonInstance();
        petSitterDetailsPresenter = new PetSitterDetailsPresenter(this);
        favoritesPetSitPresenter = new FavoritesPetSitPresenter(this);
        petSitterRatingPresenter = new PetSitterRatingPresenter(this);
        positionInList = petSitterSingletonClass.getPositionInList();

        back.setOnMouseClicked(e -> back());

        // For adding to favorites
        noFavoritesIcon.setOnMouseClicked(e -> setToFavorites());
        favoritesIcon.setOnMouseClicked(e -> setToFavorites());

        // For zoom
        pictureFrame.setOnMouseClicked(e -> {
            try {
                zoomImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // For pet sitter rating
        likeImage.setOnMouseClicked(e -> {
            ratingState = Rating.FROM_LIKE_TO_NONE;
            ratePetSitter();
        });
        dislikeImage.setOnMouseClicked(e -> {
            ratingState = Rating.FROM_DISLIKE_TO_NONE;
            ratePetSitter();
        });
        noLikeImage.setOnMouseClicked(e -> {
            switch (ratingState) {
                case NO_RATE, FROM_DISLIKE_TO_NONE, FROM_LIKE_TO_NONE -> ratingState = Rating.LIKE;
                case DISLIKE, FROM_LIKE_TO_DISLIKE -> ratingState = Rating.FROM_DISLIKE_TO_LIKE;
                default -> {
                }
            }
            ratePetSitter();
        });
        noDislikeImage.setOnMouseClicked(e -> {
            switch (ratingState) {
                case NO_RATE, FROM_DISLIKE_TO_NONE, FROM_LIKE_TO_NONE -> ratingState = Rating.DISLIKE;
                case LIKE, FROM_DISLIKE_TO_LIKE -> ratingState = Rating.FROM_LIKE_TO_DISLIKE;
                default -> {
                }
            }
            ratePetSitter();
        });

        // Load pet sitter details
        loadInfo();
    }

    @FXML
    private void back(){
        if (petSitterSingletonClass.isFromFavorites()) {
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/favoritesPetSitters.fxml");
        } else {
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitSearchResults.fxml");
        }
    }

    @FXML
    private void zoomImage() throws IOException {
        if (!defaultProfileImage.isVisible()) {
            profileImageSingletonClass.setImage(profileImage.getImage());
            Stage zoom = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/imageZoom.fxml")));
            Image appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
            zoom.getIcons().add(appIcon);
            Scene scene = new Scene(root);
            zoom.setScene(scene);
            zoom.show();
        }
    }

    private void loadInfo() {
        petSitterDetailsPresenter.loadPetSitterDetails(userSingletonClass.getUsername(), petSitterSingletonClass.getUsername());
    }

    @FXML
    private void setToFavorites(){
        favoritesPetSitPresenter.setFavorite(userSingletonClass.getUsername(), username.getText().trim());
    }

    @FXML
    private void ratePetSitter(){
        petSitterRatingPresenter.ratePetSitter(userSingletonClass.getUsername(), username.getText().trim(), ratingState.ordinal());
    }

    @Override
    public void onSetFavoriteFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onSetFavoriteSuccess() {
        if (noFavoritesIcon.isVisible()) {
            favoritesIcon.setVisible(true);
            noFavoritesIcon.setVisible(false);
            // View the change of favorite state also in the list
            petSitterResultsSingletonClass.getFavorites().set(positionInList, true);
        } else {
            noFavoritesIcon.setVisible(true);
            favoritesIcon.setVisible(false);
            // View the change of favorite state also in the list
            petSitterResultsSingletonClass.getFavorites().set(positionInList, false);
        }
    }

    @Override
    public void onRateFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onRateSuccess() {

        switch (ratingState) {
            case LIKE -> {
                likeImage.setVisible(true);
                noLikeImage.setVisible(false);
                likes.setText(String.valueOf(Integer.parseInt(likes.getText()) + 1));
            }
            case DISLIKE -> {
                dislikeImage.setVisible(true);
                noDislikeImage.setVisible(false);
                dislikes.setText(String.valueOf(Integer.parseInt(dislikes.getText()) + 1));
            }
            case FROM_LIKE_TO_DISLIKE -> {
                dislikeImage.setVisible(true);
                noLikeImage.setVisible(true);
                noDislikeImage.setVisible(false);
                likeImage.setVisible(false);
                likes.setText(String.valueOf(Integer.parseInt(likes.getText()) - 1));
                dislikes.setText(String.valueOf(Integer.parseInt(dislikes.getText()) + 1));
            }
            case FROM_DISLIKE_TO_LIKE -> {
                likeImage.setVisible(true);
                noDislikeImage.setVisible(true);
                noLikeImage.setVisible(false);
                dislikeImage.setVisible(false);
                likes.setText(String.valueOf(Integer.parseInt(likes.getText()) + 1));
                dislikes.setText(String.valueOf(Integer.parseInt(dislikes.getText()) - 1));
            }
            case FROM_LIKE_TO_NONE -> {
                noLikeImage.setVisible(true);
                likeImage.setVisible(false);
                likes.setText(String.valueOf(Integer.parseInt(likes.getText()) - 1));
            }
            case FROM_DISLIKE_TO_NONE -> {
                noDislikeImage.setVisible(true);
                dislikeImage.setVisible(false);
                dislikes.setText(String.valueOf(Integer.parseInt(dislikes.getText()) - 1));
            }
            default -> {
            }
        }

        // View the change of likes also in the list
        petSitterResultsSingletonClass.getPetSitProfileInfos().get(positionInList).setNumLikes(Integer.parseInt(likes.getText()));
        petSitterResultsSingletonClass.getPetSitProfileInfos().get(positionInList).setNumDislikes(Integer.parseInt(dislikes.getText()));

    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.setVisible(true);
        progressIndicator.requestFocus();
    }

    @Override
    public void onLoadDetailsFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadDetailsSuccess(PetSitterDetails petSitterDetails) {
        // Load username
        username.setText(petSitterSingletonClass.getUsername());

        // Load photo
        if (petSitterDetails.getLoadPetSitProfileInfo().getImage() != null) {
            profileImage.setImage(petSitterDetails.getLoadPetSitProfileInfo().getImage());
            profileImageSingletonClass.setImage(petSitterDetails.getLoadPetSitProfileInfo().getImage());
            pictureFrame.setCursor(Cursor.HAND);
        } else {
            defaultProfileImage.setVisible(true);
            profileImageSingletonClass.setImage(null);
            pictureFrame.setCursor(Cursor.DEFAULT);
        }

        // Load texts
        likes.setText(String.valueOf(petSitterDetails.getLoadPetSitProfileInfo().getNumLikes()));
        dislikes.setText(String.valueOf(petSitterDetails.getLoadPetSitProfileInfo().getNumDislikes()));
        name.setText(petSitterDetails.getProfileUserData().getName() + " " + petSitterDetails.getProfileUserData().getSurname());
        place.setText(petSitterDetails.getProfileUserData().getProvince());
        phoneNumber.setText(petSitterDetails.getProfileUserData().getPhoneNumb());
        email.setText(petSitterDetails.getEmail());
        petSitterDescription.setText(petSitterDetails.getPetSitServices().getDescription());

        // Load cared pets
        dogBox.setSelected(petSitterDetails.getPetSitCaredPets().isDog());
        catBox.setSelected(petSitterDetails.getPetSitCaredPets().isCat());
        otherPetsBox.setSelected(petSitterDetails.getPetSitCaredPets().isOtherPets());

        // Load services
        service1.setSelected(petSitterDetails.getPetSitServices().isServ1());
        service2.setSelected(petSitterDetails.getPetSitServices().isServ2());
        service3.setSelected(petSitterDetails.getPetSitServices().isServ3());
        service4.setSelected(petSitterDetails.getPetSitServices().isServ4());
        service5.setSelected(petSitterDetails.getPetSitServices().isServ5());

        // Load favorite
        if (petSitterDetails.getPetSitterRating().isFavorite()) {
            favoritesIcon.setVisible(true);
            noFavoritesIcon.setVisible(false);
        } else {
            noFavoritesIcon.setVisible(true);
            favoritesIcon.setVisible(false);
        }

        // Load rating
        if (petSitterDetails.getPetSitterRating().getRating() == 1) {
            likeImage.setVisible(true);
            noLikeImage.setVisible(false);
            ratingState = Rating.LIKE;
        } else if (petSitterDetails.getPetSitterRating().getRating() == 2) {
            dislikeImage.setVisible(true);
            noDislikeImage.setVisible(false);
            ratingState = Rating.DISLIKE;
        } else {
            ratingState = Rating.NO_RATE;
        }

    }
}
