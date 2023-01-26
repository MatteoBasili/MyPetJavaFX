package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PersonalAdsController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label noResultsText;
    @FXML
    private ImageView back;
    @FXML
    private Button newAdButton;
    @FXML
    private ProgressIndicator loadProgressIndicator;

    private UserSingletonClass userSingletonClass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();

        back.setOnMouseClicked(e -> back());

        newAdButton.setOnAction(this::handleNewAdButton);

        displayNoResults();
    }

    @FXML
    private void back(){
        if (userSingletonClass.getRole() == 1) {     // Normal User
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/normalUserProfilePage.fxml");
        } else {
            anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterProfilePage.fxml");
        }
    }

    private void displayNoResults() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            hideProgressIndicator();
                            displayText();
                        });
                    }
                }, 500);
    }

    @FXML
    private void handleNewAdButton(ActionEvent event) {
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setHeaderText("Service Unavailable");
        errorAlert.setContentText("The service is currently unavailable, sorry.");
        errorAlert.showAndWait();
    }

    private void hideProgressIndicator() {
        loadProgressIndicator.setVisible(false);
    }

    private void displayText() {
        noResultsText.setVisible(true);
    }
}
