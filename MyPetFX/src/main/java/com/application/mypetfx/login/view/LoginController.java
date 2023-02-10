package com.application.mypetfx.login.view;

import com.application.mypetfx.login.LoginContract;
import com.application.mypetfx.login.LoginPresenter;
import com.application.mypetfx.login.data.LoginCredentials;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LoginController implements Initializable, LoginContract.LoginView {

    private static final String KEY = "Password";

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private CheckBox rememberMe;
    @FXML
    private TextField username;
    @FXML
    private PasswordField hiddenPassword;
    @FXML
    private TextField shownPassword;
    @FXML
    private Button enterButton;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label showHidePasswordText;
    @FXML
    private Button googleButton;
    @FXML
    private Button facebookButton;
    @FXML
    private Label forgotPasswordText;
    @FXML
    private Line fgPLine;
    @FXML
    private Label signUpText;
    @FXML
    private Line sgnPLine;


    private Preferences preferences;
    private LoginPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // For "Remember me" function
        preferences = Preferences.userNodeForPackage(LoginController.class);
        if (preferences != null && preferences.getBoolean("RememberMe", false)) {
            username.setText(preferences.get("Username", ""));
            hiddenPassword.setText(preferences.get(KEY, ""));
            shownPassword.setText(preferences.get(KEY, ""));
            rememberMe.setSelected(true);
        }

        // Initialize "show/hide password" function
        hiddenPassword.textProperty().bindBidirectional(shownPassword.textProperty());
        showHidePasswordText.setOnMouseClicked(e -> showPassword());

        // Initialize "forgot password" function
        forgotPasswordText.setOnMouseClicked(e -> {
            try {
                showForgotPasswordScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fgPLine.setOnMouseClicked(e -> {
            try {
                showForgotPasswordScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Initialize "sign up" function
        signUpText.setOnMouseClicked(e -> {
            try {
                showSignUpScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        sgnPLine.setOnMouseClicked(e -> {
            try {
                showSignUpScreen();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        googleButton.setOnAction(this::handleSocialLoginButton);
        facebookButton.setOnAction(this::handleSocialLoginButton);
        enterButton.setOnAction(this::handleLoginButton);
        presenter = new LoginPresenter(this);
    }

    @FXML
    private void showPassword() {
        if (showHidePasswordText.getText().equals("Show")) {
            showHidePasswordText.setText("Hide");
            showHidePasswordText.setTranslateX(5);
            shownPassword.setVisible(true);
            hiddenPassword.setVisible(false);
            showHidePasswordText.requestFocus();   // For remove text highlight
        } else {
            showHidePasswordText.setText("Show");
            showHidePasswordText.setTranslateX(-1);
            hiddenPassword.setVisible(true);
            shownPassword.setVisible(false);
            showHidePasswordText.requestFocus();   // For remove text highlight
        }

    }

    @FXML
    private void showForgotPasswordScreen() throws IOException {
        changeScene("/com/application/mypetfx/fxml/pwdRecoveryScrollBar.fxml");
    }

    @FXML
    private void showSignUpScreen() throws IOException {
        changeScene("/com/application/mypetfx/fxml/registrationScrollBar.fxml");
    }

    private void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSocialLoginButton(ActionEvent event) {
        Alert infoAlert = new Alert(Alert.AlertType.WARNING);
        infoAlert.setHeaderText("Service Unavailable");
        infoAlert.setContentText("Sorry, the service is currently unavailable.");
        infoAlert.showAndWait();
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        enterButton.setDisable(true);

        String usernameInput = username.getText().trim();
        String passwordInput = hiddenPassword.getText();

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUsername(usernameInput);
        loginCredentials.setPassword(passwordInput);

        presenter.login(loginCredentials);
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
        enterButton.setDisable(false);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onSuccess(int role) throws BackingStoreException, IOException, InterruptedException {

        if (rememberMe.isSelected()) {
            rememberLogin();
        } else {
            preferences.clear();
        }

        // Set user singleton class
        UserSingletonClass userSingletonClass = UserSingletonClass.getSingletonInstance();
        userSingletonClass.setUsername(username.getText().trim());
        userSingletonClass.setRole(role);

        Stage actualStage = (Stage) anchorPane.getScene().getWindow();
        actualStage.close();

        Thread.sleep(1000);

        // Prepare new stage
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/homeScreen.fxml")));
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("MyPet");
        Image appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
        newStage.getIcons().add(appIcon);
        newStage.setResizable(false);
        newStage.setScene(scene);

        // Lunch new stage
        newStage.show();
    }

    private void rememberLogin(){
        preferences.put("Username", username.getText().trim());
        preferences.put(KEY, hiddenPassword.getText().trim());
        preferences.putBoolean("RememberMe", true);
    }

}
