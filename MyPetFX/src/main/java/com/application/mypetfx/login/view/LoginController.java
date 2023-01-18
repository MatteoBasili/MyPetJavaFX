package com.application.mypetfx.login.view;

import com.application.mypetfx.login.LoginContract;
import com.application.mypetfx.login.LoginPresenter;
import com.application.mypetfx.login.data.LoginCredentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LoginController implements Initializable, LoginContract.LoginView {

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

    private Preferences preferences;
    private LoginPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preferences = Preferences.userNodeForPackage(LoginController.class);
        if (preferences != null) {
            if (preferences.getBoolean("RememberMe", false)) {
                username.setText(preferences.get("Username", ""));
                hiddenPassword.setText(preferences.get("Password", ""));
                rememberMe.setSelected(true);
            }

        }

        /*showHidePasswordText.setOnMouseClicked(mouseEvent -> {
            if (hiddenPassword.isVisible()) {
                showHidePasswordText.setText("Hide");
                shownPassword.setVisible(true);
            } else {
                showHidePasswordText.setText("Show");
                hiddenPassword.setVisible(true);
            }
        });*/
        googleButton.setOnAction(this::handleSocialLoginButton);
        facebookButton.setOnAction(this::handleSocialLoginButton);
        enterButton.setOnAction(this::handleLoginButton);
        presenter = new LoginPresenter(this);
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
        String usernameInput = username.getText().trim();
        String passwordInput = hiddenPassword.getText().trim();

        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUsername(usernameInput);
        loginCredentials.setPassword(passwordInput);

        presenter.login(loginCredentials);
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.setVisible(true);
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void onFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onSuccess(int role) throws BackingStoreException {

        if (rememberMe.isSelected()) {
            preferences.put("Username", username.getText().trim());
            preferences.put("Password", hiddenPassword.getText().trim());
            preferences.putBoolean("RememberMe", true);
        } else {
            preferences.clear();
        }

        String welcome = switch (role) {
            case 1 -> "Welcome Normal User!";
            case 2 -> "Welcome Pet Sitter!";
            default -> "Welcome!";
        };

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText("Success!!!");
        infoAlert.setContentText(welcome);
        infoAlert.showAndWait();
    }

}
