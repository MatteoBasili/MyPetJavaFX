package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.registration.data.PetSitServices;
import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.services.profile.ServicesPresenter;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ServicesController implements Initializable, ProfileContract.ServicesView {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView back;

    @FXML
    private TextArea description;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Button saveButton;

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

    private UserSingletonClass userSingletonClass;
    private ServicesPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        presenter = new ServicesPresenter(this);

        back.setOnMouseClicked(e -> back());
        saveButton.setOnAction(this::saveServices);

        // Load cared pets
        loadServices();
    }

    @FXML
    private void back(){
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterProfilePage.fxml");
    }

    private void loadServices() {
        presenter.loadServices(userSingletonClass.getUsername());
    }

    @FXML
    private void saveServices(ActionEvent event) {
        String user = userSingletonClass.getUsername();
        boolean serv1 = service1.isSelected();
        boolean serv2 = service2.isSelected();
        boolean serv3 = service3.isSelected();
        boolean serv4 = service4.isSelected();
        boolean serv5 = service5.isSelected();
        String descriptionInput;
        if (description.getText() != null) {
            descriptionInput = description.getText().trim();
        } else {
            descriptionInput = "";
        }
        PetSitServices petSitServices = new PetSitServices();
        petSitServices.setServ1(serv1);
        petSitServices.setServ2(serv2);
        petSitServices.setServ3(serv3);
        petSitServices.setServ4(serv4);
        petSitServices.setServ5(serv5);
        petSitServices.setDescription(descriptionInput);

        presenter.saveServices(user, petSitServices);
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void onLoadServicesFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadServicesSuccess(PetSitServices petSitServices) {
        service1.setSelected(petSitServices.isServ1());
        service2.setSelected(petSitServices.isServ2());
        service3.setSelected(petSitServices.isServ3());
        service4.setSelected(petSitServices.isServ4());
        service5.setSelected(petSitServices.isServ5());
        description.setText(petSitServices.getDescription());
    }

    @Override
    public void onStoreServicesFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onStoreServicesSuccess() {
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
