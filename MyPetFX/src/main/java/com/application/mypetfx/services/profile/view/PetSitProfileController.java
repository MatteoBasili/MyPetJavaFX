package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.profile.PetSitProfilePresenter;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;
import com.application.mypetfx.utils.images.Zoom;
import com.application.mypetfx.utils.singleton_examples.ProfileImageSingletonClass;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PetSitProfileController implements Initializable, ProfileContract.PetSitProfileView {

    private static final Logger logger = Logger.getLogger(PetSitProfileController.class);

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label username;
    @FXML
    private AnchorPane pictureFrame;
    @FXML
    private ImageView changePhoto;

    @FXML
    private ImageView deletePhoto;

    @FXML
    private ImageView defaultProfileImage;
    @FXML
    private ImageView profileImage;
    @FXML
    private ImageView savePhoto;
    @FXML
    private ProgressIndicator loadProgressIndicator;
    @FXML
    private ProgressIndicator saveProgressIndicator;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;
    @FXML
    private Button adsButton;
    @FXML
    private Button caredPetsButton;
    @FXML
    private Button personalInfoButton;
    @FXML
    private Button servicesButton;

    private UserSingletonClass userSingletonClass;
    private ProfileImageSingletonClass profileImageSingletonClass;

    private PetSitProfilePresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        profileImageSingletonClass = ProfileImageSingletonClass.getSingletonInstance();
        presenter = new PetSitProfilePresenter(this);

        // Load photo and likes and dislikes
        loadProfile();

        // For zoom
        pictureFrame.setOnMouseClicked(e -> zoomImage());

        // For image change
        changePhoto.setOnMouseClicked(e -> changePhoto());

        // For image deletion
        deletePhoto.setOnMouseClicked(e -> deletePhoto());

        // For image saving
        savePhoto.setOnMouseClicked(e -> saveProfilePhoto());

        // For personal info button
        personalInfoButton.setOnAction(this::showPersonalInfoPage);

        // For ads button
        adsButton.setOnAction(this::showPersonalAdsPage);

        // For cared pets button
        caredPetsButton.setOnAction(this::showCaredPetsPage);

        // For services button
        servicesButton.setOnAction(this::showServicesPage);
    }

    @FXML
    private void zoomImage() {
        if (!defaultProfileImage.isVisible()) {
            profileImageSingletonClass.setImage(profileImage.getImage());
            Zoom zoom = new Zoom();
            zoom.zoomImage();
        }
    }

    @FXML
    private void changePhoto() {
        Stage stage = (Stage) changePhoto.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload image");
        File filePath = fileChooser.showOpenDialog(stage);

        // Try to update the image by loading the new image
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            profileImage.setImage(image);
            profileImageSingletonClass.setImage(image);
            defaultProfileImage.setVisible(false);
            pictureFrame.setCursor(Cursor.HAND);
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        }
    }

    @FXML
    private void deletePhoto() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to delete profile image?");
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait();
        if (alert.getResult().equals(yes)) {
            defaultProfileImage.setVisible(true);
            profileImage.setImage(null);
            profileImageSingletonClass.setImage(null);
            pictureFrame.setCursor(Cursor.DEFAULT);
        }
    }

    @FXML
    private void saveProfilePhoto() {
        String user = userSingletonClass.getUsername();
        Image image = profileImageSingletonClass.getImage();

        presenter.savePhoto(user, image);
    }

    @FXML
    private void showPersonalInfoPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/personalInfoPage.fxml");
    }

    @FXML
    private void showPersonalAdsPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/personalAdsPage.fxml");
    }

    @FXML
    private void showCaredPetsPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/caredPetsPage.fxml");
    }

    @FXML
    private void showServicesPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/servicesPage.fxml");
    }

    private void loadProfile() {
        presenter.loadProfile(userSingletonClass.getUsername());
    }

    @Override
    public void hideLoadProgressIndicator() {
        loadProgressIndicator.setVisible(false);
    }

    @Override
    public void hideSaveProgressIndicator() {
        saveProgressIndicator.setVisible(false);
    }

    @Override
    public void onLoadProfileFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadProfileSuccess(LoadPetSitProfileInfo loadPetSitProfileInfo) {
        if (loadPetSitProfileInfo.getImage() != null) {
            profileImage.setImage(loadPetSitProfileInfo.getImage());
            profileImageSingletonClass.setImage(loadPetSitProfileInfo.getImage());
            pictureFrame.setCursor(Cursor.HAND);
        } else {
            defaultProfileImage.setVisible(true);
            profileImageSingletonClass.setImage(null);
            pictureFrame.setCursor(Cursor.DEFAULT);
        }
        username.setText(userSingletonClass.getUsername());
        likes.setText(String.valueOf(loadPetSitProfileInfo.getNumLikes()));
        dislikes.setText(String.valueOf(loadPetSitProfileInfo.getNumDislikes()));
    }

    @Override
    public void onStorePhotoFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onStorePhotoSuccess() {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Success");
        errorAlert.setContentText("Profile image saved.");
        errorAlert.showAndWait();
    }

    @Override
    public void showSaveProgressIndicator() {
        saveProgressIndicator.setVisible(true);
        saveProgressIndicator.requestFocus();
    }
}
