package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.registration.data.PetSitCaredPets;
import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.profile.CaredPetsPresenter;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CaredPetsController implements Initializable, ProfileContract.CaredPetsView {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView back;
    @FXML
    private CheckBox catBox;
    @FXML
    private CheckBox dogBox;
    @FXML
    private CheckBox otherPetsBox;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button saveButton;
    private UserSingletonClass userSingletonClass;
    private CaredPetsPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        presenter = new CaredPetsPresenter(this);

        back.setOnMouseClicked(e -> back());
        saveButton.setOnAction(this::saveCaredPets);

        // Load cared pets
        loadCaredPets();
    }

    @FXML
    private void back(){
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterProfilePage.fxml");
    }

    private void loadCaredPets() {
        presenter.loadCaredPets(userSingletonClass.getUsername());
    }

    @FXML
    private void saveCaredPets(ActionEvent event) {
        String user = userSingletonClass.getUsername();
        boolean dog = dogBox.isSelected();
        boolean cat = catBox.isSelected();
        boolean otherPets = otherPetsBox.isSelected();

        PetSitCaredPets petSitCaredPets = new PetSitCaredPets();
        petSitCaredPets.setDog(dog);
        petSitCaredPets.setCat(cat);
        petSitCaredPets.setOtherPets(otherPets);

        presenter.saveCaredPets(user, petSitCaredPets);
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void onLoadPetsFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadPetsSuccess(PetSitCaredPets petSitCaredPets) {
        dogBox.setSelected(petSitCaredPets.isDog());
        catBox.setSelected(petSitCaredPets.isCat());
        otherPetsBox.setSelected(petSitCaredPets.isOtherPets());
    }

    @Override
    public void onStorePetsFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onStorePetsSuccess() {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Success");
        errorAlert.setContentText("Informations saved.");
        errorAlert.showAndWait();
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.setVisible(true);
        progressIndicator.requestFocus();
    }
}
