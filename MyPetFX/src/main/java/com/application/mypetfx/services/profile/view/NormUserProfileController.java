package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class NormUserProfileController implements Initializable {

    @FXML
    private Label username;

    private UserSingletonClass userSingletonClass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();

        username.setText(userSingletonClass.getUsername());
    }
}
