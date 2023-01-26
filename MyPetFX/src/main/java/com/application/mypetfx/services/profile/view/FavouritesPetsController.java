package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.DashboardController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class FavouritesPetsController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ProgressIndicator loadProgressIndicator;
    @FXML
    private Label noResultsText;
    @FXML
    private Label favoritesPetSitters;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        favoritesPetSitters.setOnMouseClicked(e -> showPetSitters());

        displayNoResults();
    }

    @FXML
    private void showPetSitters() {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/favoritesPetSitters.fxml");
    }

    private void displayNoResults() {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            loadProgressIndicator.setVisible(false);
                            noResultsText.setVisible(true);
                        });
                    }
                }, 250);
    }

}
