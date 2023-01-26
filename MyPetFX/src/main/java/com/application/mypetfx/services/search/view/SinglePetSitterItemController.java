package com.application.mypetfx.services.search.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.search.FavoritesPetSitPresenter;
import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import com.application.mypetfx.utils.singleton_examples.PetSitterSingletonClass;
import com.application.mypetfx.utils.singleton_examples.ProfileImageSingletonClass;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SinglePetSitterItemController implements Initializable, PetSitterSearchContract.PetSitterFavoritesView {

    @FXML
    private AnchorPane pictureFrame;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView defaultProfileImage;
    @FXML
    private Label username;
    @FXML
    private Label region;
    @FXML
    private Label province;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;
    @FXML
    private ImageView noFavoritesIcon;
    @FXML
    private ImageView favoritesIcon;
    @FXML
    private Button moreDetailsButton;

    private UserSingletonClass userSingletonClass;
    private ProfileImageSingletonClass profileImageSingletonClass;
    private PetSitterResultsSingletonClass petSitterResultsSingletonClass;
    private PetSitterSingletonClass petSitterSingletonClass;
    private FavoritesPetSitPresenter presenter;
    private int listPosition;
    private boolean isFromFavourites;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        profileImageSingletonClass = ProfileImageSingletonClass.getSingletonInstance();
        petSitterResultsSingletonClass = PetSitterResultsSingletonClass.getSingletonInstance();
        petSitterSingletonClass = PetSitterSingletonClass.getSingletonInstance();
        presenter = new FavoritesPetSitPresenter(this);

        // For zoom
        pictureFrame.setOnMouseClicked(e -> {
            try {
                zoomImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // For adding to favorites
        noFavoritesIcon.setOnMouseClicked(e -> setToFavorites());
        favoritesIcon.setOnMouseClicked(e -> setToFavorites());

        // For pet sitter details button
        moreDetailsButton.setOnAction(this::showMoreDetails);
    }

    public void setListPosition(int i) {
        this.listPosition = i;
    }

    public void setSource(boolean isFromFavourites) {
        this.isFromFavourites = isFromFavourites;
    }

    public void setPhoto(Image image) {
        if (image != null) {
            profileImage.setImage(image);
            defaultProfileImage.setVisible(false);
            pictureFrame.setCursor(Cursor.HAND);
        } else {
            defaultProfileImage.setVisible(true);
            profileImage.setImage(null);
            pictureFrame.setCursor(Cursor.DEFAULT);
        }
    }

    public void setFavorite(boolean bool) {
        if (bool) {
            favoritesIcon.setVisible(true);
            noFavoritesIcon.setVisible(false);
        }
    }

    public void setUsername(String string) {
        username.setText(string);
    }

    public void setRegion(String  region) {
        this.region.setText(region);
    }

    public void setProvince(String province) {
        this.province.setText(province);
    }

    public void setLikes(int likes) {
        this.likes.setText(String.valueOf(likes));
    }

    public void setDislikes(int dislikes) {
        this.dislikes.setText(String.valueOf(dislikes));
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

    @FXML
    private void showMoreDetails(ActionEvent event) {
        petSitterSingletonClass.setUsername(username.getText().trim());
        petSitterSingletonClass.setPositionInList(listPosition);
        petSitterSingletonClass.setFromFavorites(isFromFavourites);
        AnchorPane pane = petSitterResultsSingletonClass.getAnchorPane();
        petSitterResultsSingletonClass.setAnchorPane(DashboardController.changeScreen(pane, "/com/application/mypetfx/fxml/petSitterDetailsPage.fxml"));
    }

    @FXML
    private void setToFavorites(){
        presenter.setFavorite(userSingletonClass.getUsername(), username.getText().trim());
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
            petSitterResultsSingletonClass.getFavorites().set(listPosition, true);
        } else {
            noFavoritesIcon.setVisible(true);
            favoritesIcon.setVisible(false);
            // View the change of favorite state also in the list
            petSitterResultsSingletonClass.getFavorites().set(listPosition, false);
        }
    }
}
