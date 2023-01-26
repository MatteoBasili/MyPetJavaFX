package com.application.mypetfx.splash_screen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private Label progress;

    private static Label label;

    @FXML
    private ProgressBar progressBar;

    private static ProgressBar statProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        label = progress;
        statProgressBar = progressBar;

    }

    public static void setLabel(String text) {
        SplashScreenController.label.setText(text);
    }

    public static void setStatProgressBar(double progress) {
        SplashScreenController.statProgressBar.setProgress(progress);
    }

}
