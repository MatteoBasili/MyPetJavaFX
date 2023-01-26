package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NormUserProfileController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label username;
    @FXML
    private Button personalInfoButton;
    @FXML
    private Button adsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSingletonClass userSingletonClass = UserSingletonClass.getSingletonInstance();
        username.setText(userSingletonClass.getUsername());

        // For personal info button
        personalInfoButton.setOnAction(this::showPersonalInfoPage);

        // For ads button
        adsButton.setOnAction(this::showPersonalAdsPage);
    }

    @FXML
    private void showPersonalInfoPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/personalInfoPage.fxml");
    }

    @FXML
    private void showPersonalAdsPage(ActionEvent event) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/personalAdsPage.fxml");
    }
}
