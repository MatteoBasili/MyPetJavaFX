package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.utils.singleton_examples.ProfileImageSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageZoomController implements Initializable {

    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProfileImageSingletonClass profileImageSingletonClass = ProfileImageSingletonClass.getSingletonInstance();
        image.setImage(profileImageSingletonClass.getImage());
    }
}
