package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.profile.PetSitProfilePresenter;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;
import com.application.mypetfx.utils.singleton_examples.ProfileImageSingletonClass;
import javafx.embed.swing.SwingFXUtils;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PetSitProfileController implements Initializable, ProfileContract.PetSitProfileView {

    private static final Logger logger = Logger.getLogger(PetSitProfileController.class);

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

    private UserSingletonClass userSingletonClass;
    private ProfileImageSingletonClass profileImageSingletonClass;
    private FileChooser fileChooser;
    private File filePath;

    private PetSitProfilePresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        profileImageSingletonClass = ProfileImageSingletonClass.getSingletonInstance();
        presenter = new PetSitProfilePresenter(this);

        loadProfile();

        // For zoom
        pictureFrame.setOnMouseClicked(e -> {
            try {
                zoomImage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // For image change
        changePhoto.setOnMouseClicked(e -> changePhoto());

        // For image deletion
        deletePhoto.setOnMouseClicked(e -> deletePhoto());

        // For image saving
        savePhoto.setOnMouseClicked(e -> saveProfilePhoto());
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
    private void changePhoto() {
        Stage stage = (Stage) changePhoto.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Upload image");
        this.filePath = fileChooser.showOpenDialog(stage);

        // Try to update the image by loading the new image
        try {
            BufferedImage bufferedImage = ImageIO.read(filePath);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            profileImage.setImage(image);
            profileImageSingletonClass.setImage(image);
            defaultProfileImage.setVisible(false);
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
        }
    }

    @FXML
    private void saveProfilePhoto() {
        String user = userSingletonClass.getUsername();
        Image image = profileImageSingletonClass.getImage();

        presenter.savePhoto(user, image);
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
        } else {
            defaultProfileImage.setVisible(true);
            profileImageSingletonClass.setImage(null);
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
