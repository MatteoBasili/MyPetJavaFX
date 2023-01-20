package com.application.mypetfx.registration.view;

import com.application.mypetfx.pwd_recovery.PwdRecoveryPresenter;
import com.application.mypetfx.registration.RegistrationContract;
import com.application.mypetfx.utils.factory_method_example.ProvincesFactory;
import com.application.mypetfx.utils.factory_method_example.Regions;
import com.application.mypetfx.utils.factory_method_example.provinces.ProvincesListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable, RegistrationContract.RegistrationView {

    private List<String> dynamicProvincesList;
    private ProvincesFactory provincesFactory;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> regionsBox;
    @FXML
    private ComboBox<String> provincesBox;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView back;
    @FXML
    private ProgressIndicator progressIndicator;

    private PwdRecoveryPresenter presenter;
    private ProvincesListBase provincesListBase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();
    }

    private void initializeChoiceBoxes() {
        dynamicProvincesList = new ArrayList<>();
        regionsBox.setPromptText("Select your region");
        provincesBox.setPromptText("Select the region first");
        regionsBox.getItems().addAll(new Regions().createRegionList());
        provincesFactory = new ProvincesFactory();

        regionsBox.setOnAction(this::loadProvincesList);
        back.setOnMouseClicked(e -> {
            try {
                backToLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void loadProvincesList(ActionEvent event) {
        try{
            provincesBox.getItems().clear();
            provincesBox.setDisable(false);
            provincesBox.setPromptText("Select your province");
            this.provincesListBase = provincesFactory.createProvinceBaseList(regionsBox.getSelectionModel().getSelectedIndex() + 1);
            dynamicProvincesList = provincesListBase.createProvincesList();
            provincesBox.getItems().addAll(dynamicProvincesList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToLogin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/loginScreen.fxml")));
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.setVisible(true);
        progressIndicator.requestFocus();
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void onFailed(String message) {
        registerButton.setDisable(false);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onSuccess() throws IOException {
        registerButton.setDisable(false);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText("Email sent");
        infoAlert.setContentText("The email has been sent to your email address.");
        infoAlert.showAndWait();

        backToLogin();
    }

}
