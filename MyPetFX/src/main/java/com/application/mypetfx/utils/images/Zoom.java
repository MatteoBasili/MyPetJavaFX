package com.application.mypetfx.utils.images;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Zoom {

    public void zoomImage() {
        try {
            Stage zoom = new Stage();
            Parent root;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/imageZoom.fxml")));
            Image appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
            zoom.getIcons().add(appIcon);
            Scene scene = new Scene(root);
            zoom.setScene(scene);
            zoom.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
