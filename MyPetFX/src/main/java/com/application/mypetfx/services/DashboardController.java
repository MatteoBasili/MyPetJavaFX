package com.application.mypetfx.services;

import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private static final Logger logger = Logger.getLogger(DashboardController.class);

    @FXML
    private AnchorPane anchorPane, actualScreen;
    @FXML
    private VBox vBox;

    private UserSingletonClass userSingletonClass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userSingletonClass = UserSingletonClass.getSingletonInstance();

        changeScreen("/com/application/mypetfx/fxml/searchPage.fxml");

        for (Node node : vBox.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Search Page" -> changeScreen("/com/application/mypetfx/fxml/searchPage.fxml");
                        case "Map Page" -> changeScreen("/com/application/mypetfx/fxml/mapPage.fxml");
                        case "Favorites Page" -> changeScreen("/com/application/mypetfx/fxml/favoritesPage.fxml");
                        case "Profile Page" -> {
                            if (userSingletonClass.getRole() == 1) {   // User is a normal user
                                changeScreen("/com/application/mypetfx/fxml/normalUserProfilePage.fxml");
                            } else {
                                changeScreen("/com/application/mypetfx/fxml/petSitterProfilePage.fxml");
                            }
                        }
                        case "Author Info Page" -> changeScreen("/com/application/mypetfx/fxml/authorInfoPage.fxml");
                        case "Logout" -> logout();
                    }
                });
            }
        }

    }

    private void changeScreen(String fxml) {
        try {
            actualScreen.getChildren().clear();
            AnchorPane nextScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            actualScreen.getChildren().add(nextScreen);
            nextScreen.toFront();
            actualScreen = nextScreen;
        } catch (IOException ex) {
            logger.error("IO Error: ", ex);
        }
    }

    private void logout() {
            try {
                Stage actualStage = (Stage) anchorPane.getScene().getWindow();
                actualStage.close();

                Thread.sleep(1000);

                Parent root;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/loginScreenScrollBar.fxml")));
                Stage loginStage = new Stage();
                Scene scene = new Scene(root);
                loginStage.setTitle("MyPet");
                Image appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
                loginStage.getIcons().add(appIcon);
                loginStage.setScene(scene);

                // Lunch Login Stage
                loginStage.show();
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
    }
}
