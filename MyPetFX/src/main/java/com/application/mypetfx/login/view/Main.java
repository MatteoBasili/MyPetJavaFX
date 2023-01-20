package com.application.mypetfx.login.view;

import com.application.mypetfx.splash_screen.MyPreloader;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static final int COUNT_LIMIT = 100;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/loginScreen.fxml")));
        Scene scene = new Scene(root);

        stage.setTitle("MyPet");

        Image appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");
        stage.getIcons().add(appIcon);

        //stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {

        // Perform some heavy lifting (i.e. database start, check for application updates, etc.)
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (double) i/100;
            notifyPreloader(new Preloader.ProgressNotification(progress));
            Thread.sleep(/*50*/0);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("javafx.preloader", MyPreloader.class.getCanonicalName());
        Application.launch(Main.class, args);

    }
}