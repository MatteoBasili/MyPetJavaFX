package com.application.mypetfx.services.search.view;

import com.application.mypetfx.services.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchMenuController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button petShopsButton;
    @FXML
    private Button petSittersButton;
    @FXML
    private Button petsButton;
    @FXML
    private Button veterinariansButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Handle pet sitters search button
        petSittersButton.setOnAction(this::handlePetSittersSearchButton);

        // Search for pets not implemented
        petsButton.setOnAction(this::handlePetsSearchButton);

        // Show map for veterinarians and pet shops
        veterinariansButton.setOnAction(this::showMapPage);
        petShopsButton.setOnAction(this::showMapPage);
    }

    @FXML
    private void showMapPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/mapPage.fxml");
    }

    @FXML
    private void handlePetsSearchButton(ActionEvent event) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText("The service is currently unavailable, sorry.");
        errorAlert.showAndWait();
    }

    @FXML
    private void handlePetSittersSearchButton(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterSearchMenu.fxml");
    }

}
