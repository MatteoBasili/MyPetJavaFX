package com.application.mypetfx.splash_screen;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashScreen extends Application {

    private static final int COUNT_LIMIT = 100;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        /*stage.setTitle("MyPet");

        Image image = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
        stage.getIcons().add(image);*/

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {

        // Perform some heavy lifting (i.e. database start, check for application updates, etc.)
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (double) i/100;
            System.out.println("progress: " + progress);
            notifyPreloader(new Preloader.ProgressNotification(progress));
            Thread.sleep(50);
        }

    }

    /**
     * @param args the coomand line arguments
     */
    public static void main(String[] args) {

        System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        Application.launch(SplashScreen.class, args);

    }
}