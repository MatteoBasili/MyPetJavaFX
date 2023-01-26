package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.registration.data.ProfileUserData;
import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.profile.PersonalInfoPresenter;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.services.profile.data.PersonalInfo;
import com.application.mypetfx.utils.factory_method_example.ProvincesFactory;
import com.application.mypetfx.utils.factory_method_example.Regions;
import com.application.mypetfx.utils.factory_method_example.provinces.ProvincesListBase;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PersonalInfoController implements Initializable, ProfileContract.PersonalInfoView {

    private static final Logger logger = Logger.getLogger(PersonalInfoController.class);

    @FXML
    private TextField address;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView back;

    @FXML
    private TextField email;

    @FXML
    private TextField firstPetName;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ComboBox<String> provincesBox;

    @FXML
    private ComboBox<String> regionsBox;

    @FXML
    private Button saveButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private TextField surname;

    private UserSingletonClass userSingletonClass;
    private PersonalInfoPresenter presenter;

    private ProvincesFactory provincesFactory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        presenter = new PersonalInfoPresenter(this);

        initializeComboBoxes();

        back.setOnMouseClicked(e -> back());
        saveButton.setOnAction(this::saveInfo);

        // Load personal information
        loadInfo();
    }

    private void initializeComboBoxes() {
        regionsBox.getItems().addAll(new Regions().createRegionList());
        provincesFactory = new ProvincesFactory();

        regionsBox.setOnAction(this::loadProvincesList);
    }

    @FXML
    private void loadProvincesList(ActionEvent event) {
        try{
            provincesBox.getItems().clear();
            provincesBox.setPromptText("Select your province");
            ProvincesListBase provincesListBase = provincesFactory.createProvinceBaseList(regionsBox.getSelectionModel().getSelectedIndex() + 1);
            List<String> dynamicProvincesList = provincesListBase.createProvincesList();
            provincesBox.getItems().addAll(dynamicProvincesList);
        }
        catch (Exception e) {
            logger.error("Error: ", e);
        }
    }

    @FXML
    private void back(){
        if (userSingletonClass.getRole() == 1) {     // Normal User
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/normalUserProfilePage.fxml");
        } else {
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterProfilePage.fxml");
        }
    }

    private void loadInfo() {
        presenter.loadInfo(userSingletonClass.getUsername());
    }

    @FXML
    private void saveInfo(ActionEvent event) {
        saveButton.setDisable(true);
        String user = userSingletonClass.getUsername();
        String nameInput = name.getText().trim();
        String surnameInput = surname.getText().trim();
        String regionInput = (regionsBox.getSelectionModel().getSelectedItem() == null) ? "" : regionsBox.getSelectionModel().getSelectedItem();
        String provinceInput = (provincesBox.getSelectionModel().getSelectedItem() == null) ? "" : provincesBox.getSelectionModel().getSelectedItem();
        String addressInput = address.getText().trim();
        String phoneNumbInput = phoneNumber.getText().trim();
        String emailInput = email.getText().trim();
        String firstPetNameInput = firstPetName.getText().trim();

        ProfileUserData profileUserData = new ProfileUserData();
        profileUserData.setAddress(addressInput);
        profileUserData.setName(nameInput);
        profileUserData.setPhoneNumb(phoneNumbInput);
        profileUserData.setProvince(provinceInput);
        profileUserData.setRegion(regionInput);
        profileUserData.setSurname(surnameInput);

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstPetName(firstPetNameInput);
        personalInfo.setEmail(emailInput);
        personalInfo.setProfileUserData(profileUserData);

        presenter.saveInfo(user, personalInfo);
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
    public void onLoadInfoFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadInfoSuccess(PersonalInfo personalInfo) {
        name.setText(personalInfo.getProfileUserData().getName());
        surname.setText(personalInfo.getProfileUserData().getSurname());
        regionsBox.getSelectionModel().select(personalInfo.getProfileUserData().getRegion());
        provincesBox.getSelectionModel().select(personalInfo.getProfileUserData().getProvince());
        address.setText(personalInfo.getProfileUserData().getAddress());
        phoneNumber.setText(personalInfo.getProfileUserData().getPhoneNumb());
        email.setText(personalInfo.getEmail());
        firstPetName.setText(personalInfo.getFirstPetName());
    }

    @Override
    public void onStoreInfoFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
        saveButton.setDisable(false);
    }

    @Override
    public void onStoreInfoSuccess() {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Success");
        errorAlert.setContentText("Informations saved.");
        errorAlert.showAndWait();
        saveButton.setDisable(false);
    }

}
