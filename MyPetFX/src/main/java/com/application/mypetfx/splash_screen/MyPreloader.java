package com.application.mypetfx.splash_screen;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;
    private Image appIcon;

    public MyPreloader() {

    }

    @Override
    public void init() throws Exception {

        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("splashScreen.fxml")));
        scene = new Scene(root1);
        appIcon = new Image("D:\\Matteo\\ISPW-E-BD\\ISPW\\2021-22\\Progetto\\CODICE\\JavaFX\\Codice\\MyPetFX\\src\\main\\resources\\com\\application\\mypetfx\\icons\\app_icon.png");

    }

    @Override
    public void start(Stage primaryStage) {

        this.preloaderStage = primaryStage;
        preloaderStage.getIcons().add(appIcon);

        // Set preloader scene and show stage.
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            SplashScreenController.label.setText("Loading " + (int) (((ProgressNotification) info).getProgress() * 100) + "%");
            SplashScreenController.statProgressBar.setProgress(((ProgressNotification) info).getProgress());
        }

    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        if (Objects.requireNonNull(type) == StateChangeNotification.Type.BEFORE_START) {// Called after MyApplication#init and before MyApplication#start is called.
            preloaderStage.hide();
        }

    }

}
