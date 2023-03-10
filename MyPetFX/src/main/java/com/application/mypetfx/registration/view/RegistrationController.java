package com.application.mypetfx.registration.view;

import com.application.mypetfx.registration.RegistrationContract;
import com.application.mypetfx.registration.RegistrationPresenter;
import com.application.mypetfx.registration.data.*;
import com.application.mypetfx.utils.factory_method_example.RegionsFactory;
import com.application.mypetfx.utils.factory_method_example.Italy;
import com.application.mypetfx.utils.factory_method_example.regions.ProvincesBaseList;
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
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable, RegistrationContract.RegistrationView {

    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    private RegionsFactory regionsFactory;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private ComboBox<String> regionsBox;
    @FXML
    private ComboBox<String> provincesBox;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField firstPetName;
    @FXML
    private PasswordField hiddenPassword;
    @FXML
    private TextField shownPassword;
    @FXML
    private PasswordField hiddenPasswordConfirm;
    @FXML
    private TextField shownPasswordConfirm;
    @FXML
    private Label showHidePasswordText;
    @FXML
    private Label showHidePasswordConfirmText;
    @FXML
    private CheckBox petSitterBox;
    @FXML
    private Label servicesText;
    @FXML
    private Label caredPetsText;
    @FXML
    private Label petSitterDescriptionText;
    @FXML
    private CheckBox caredPet1;
    @FXML
    private CheckBox caredPet2;
    @FXML
    private CheckBox caredPet3;
    @FXML
    private CheckBox petSitService1;
    @FXML
    private CheckBox petSitService2;
    @FXML
    private CheckBox petSitService3;
    @FXML
    private CheckBox petSitService4;
    @FXML
    private CheckBox petSitService5;
    @FXML
    private TextArea petSitterDescription;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView back;
    @FXML
    private ProgressIndicator progressIndicator;

    private RegistrationPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeChoiceBoxes();

        // Initialize "show/hide password" function
        hiddenPassword.textProperty().bindBidirectional(shownPassword.textProperty());
        showHidePasswordText.setOnMouseClicked(e -> showText(showHidePasswordText, hiddenPassword, shownPassword));
        hiddenPasswordConfirm.textProperty().bindBidirectional(shownPasswordConfirm.textProperty());
        showHidePasswordConfirmText.setOnMouseClicked(e -> showText(showHidePasswordConfirmText, hiddenPasswordConfirm, shownPasswordConfirm));

        back.setOnMouseClicked(e -> {
            try {
                backToLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        petSitterBox.setOnAction(this::handlePetSitterRegistration);
        registerButton.setOnAction(this::handleRegisterButton);
        presenter = new RegistrationPresenter(this);
    }

    private void initializeChoiceBoxes() {
        regionsBox.setPromptText("Select your region");
        provincesBox.setPromptText("First select the region");
        regionsBox.getItems().addAll(new Italy().createRegionList());
        regionsFactory = new RegionsFactory();

        regionsBox.setOnAction(this::loadProvincesList);
    }

    @FXML
    private void loadProvincesList(ActionEvent event) {
        try{
            provincesBox.getItems().clear();
            provincesBox.setDisable(false);
            provincesBox.setPromptText("Select your province");
            ProvincesBaseList provincesBaseList = regionsFactory.createProvinceBaseList(regionsBox.getSelectionModel().getSelectedIndex() + 1);
            List<String> dynamicProvincesList = provincesBaseList.createProvincesList();
            provincesBox.getItems().addAll(dynamicProvincesList);
        }
        catch (Exception e) {
            logger.error("Error: ", e);
        }
    }

    @FXML
    private void backToLogin() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Attention!");
        alert.setContentText("Are you sure you want to exit registration?");
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait();
        if (alert.getResult().equals(yes)) {
            showLoginScreen();
        }
    }

    private void showLoginScreen() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/loginScreenScrollBar.fxml")));
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void showText(Label text, PasswordField passwordField, TextField textField) {
        if (text.getText().equals("Hide")) {
            text.setText("Show");
            text.setTranslateX(-1);
            passwordField.setVisible(true);
            textField.setVisible(false);
            text.requestFocus();   // For remove text highlight
        } else {
            text.setText("Hide");
            text.setTranslateX(5);
            textField.setVisible(true);
            passwordField.setVisible(false);
            text.requestFocus();   // For remove text highlight
        }

    }

    @FXML
    private void handlePetSitterRegistration(ActionEvent event) {
        boolean isChecked = petSitterBox.isSelected();
        servicesText.setDisable(!isChecked);
        caredPetsText.setDisable(!isChecked);
        petSitService1.setDisable(!isChecked);
        petSitService2.setDisable(!isChecked);
        petSitService3.setDisable(!isChecked);
        petSitService4.setDisable(!isChecked);
        petSitService5.setDisable(!isChecked);
        caredPet1.setDisable(!isChecked);
        caredPet2.setDisable(!isChecked);
        caredPet3.setDisable(!isChecked);
        petSitterDescriptionText.setDisable(!isChecked);
        petSitterDescription.setDisable(!isChecked);
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        registerButton.setDisable(true);

        String nameInput = name.getText().trim();
        String surnameInput = surname.getText().trim();
        String regionInput = regionsBox.getSelectionModel().getSelectedItem();
        String provinceInput = provincesBox.getSelectionModel().getSelectedItem();
        String addressInput = address.getText().trim();
        String phoneNumberInput = phoneNumber.getText().trim();
        String usernameInput = username.getText().trim();
        String emailInput = email.getText().trim();
        String passwordInput = hiddenPassword.getText();
        String passwordConfirmInput = hiddenPasswordConfirm.getText();
        String firstPetNameInput = firstPetName.getText().trim();
        boolean petSitterInput = petSitterBox.isSelected();
        boolean dogInput = caredPet1.isSelected();
        boolean catInput = caredPet2.isSelected();
        boolean otherPetsInput = caredPet3.isSelected();
        boolean service1Input = petSitService1.isSelected();
        boolean service2Input = petSitService2.isSelected();
        boolean service3Input = petSitService3.isSelected();
        boolean service4Input = petSitService4.isSelected();
        boolean service5Input = petSitService5.isSelected();
        String petSitterDescriptionInput = petSitterDescription.getText().trim();

        SystemUserData systemUserData = new SystemUserData();
        systemUserData.setFirstPetName(firstPetNameInput);
        systemUserData.setEmail(emailInput);
        systemUserData.setPassword(passwordInput);
        systemUserData.setPasswordConfirm(passwordConfirmInput);
        systemUserData.setPetSitter(petSitterInput);
        systemUserData.setUsername(usernameInput);

        ProfileUserData profileUserData = new ProfileUserData();
        profileUserData.setAddress(addressInput);
        profileUserData.setName(nameInput);
        profileUserData.setPhoneNumb(phoneNumberInput);
        profileUserData.setProvince(provinceInput);
        profileUserData.setRegion(regionInput);
        profileUserData.setSurname(surnameInput);

        PetSitServices petSitServices = new PetSitServices();
        petSitServices.setDescription(petSitterDescriptionInput);
        petSitServices.setServ1(service1Input);
        petSitServices.setServ2(service2Input);
        petSitServices.setServ3(service3Input);
        petSitServices.setServ4(service4Input);
        petSitServices.setServ5(service5Input);

        PetSitCaredPets petSitCaredPets = new PetSitCaredPets();
        petSitCaredPets.setDog(dogInput);
        petSitCaredPets.setCat(catInput);
        petSitCaredPets.setOtherPets(otherPetsInput);

        RegistrationCredentials registrationCredentials = new RegistrationCredentials();
        registrationCredentials.setPetSitCaredPets(petSitCaredPets);
        registrationCredentials.setPetSitServices(petSitServices);
        registrationCredentials.setProfileUserData(profileUserData);
        registrationCredentials.setSystemUserData(systemUserData);

        presenter.registerUser(registrationCredentials);
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
        infoAlert.setHeaderText("Registration successful");
        infoAlert.setContentText("Registration is complete, welcome to MyPet!\n" +
                "Log in now.");
        infoAlert.showAndWait();

        showLoginScreen();
    }

}
