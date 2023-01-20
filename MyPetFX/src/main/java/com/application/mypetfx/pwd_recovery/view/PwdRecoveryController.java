package com.application.mypetfx.pwd_recovery.view;

import com.application.mypetfx.pwd_recovery.PwdRecoveryContract;
import com.application.mypetfx.pwd_recovery.PwdRecoveryPresenter;
import com.application.mypetfx.pwd_recovery.data.PwdRecoveryCredentials;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PwdRecoveryController implements Initializable, PwdRecoveryContract.PWDRecoveryView {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField email;
    @FXML
    private TextField petName;
    @FXML
    private Button sendButton;
    @FXML
    private ImageView back;
    @FXML
    private ProgressIndicator progressIndicator;

    private PwdRecoveryPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendButton.setOnAction(this::handleSendEmailButton);
        back.setOnMouseClicked(e -> {
            try {
                backToLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        presenter = new PwdRecoveryPresenter(this);
    }

    @FXML
    private void backToLogin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/loginScreen.fxml")));
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSendEmailButton(ActionEvent event) {
        sendButton.setDisable(true);

        String emailInput = email.getText().trim();
        String petNameInput = petName.getText().trim();

        PwdRecoveryCredentials pwdRecoveryCredentials = new PwdRecoveryCredentials();
        pwdRecoveryCredentials.setEmail(emailInput);
        pwdRecoveryCredentials.setPetName(petNameInput);

        presenter.recoverPassword(pwdRecoveryCredentials);
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
        sendButton.setDisable(false);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onSuccess() {
        sendButton.setDisable(false);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setHeaderText("Email sent");
        infoAlert.setContentText("The email has been sent to your email address.");
        infoAlert.showAndWait();
    }

}
