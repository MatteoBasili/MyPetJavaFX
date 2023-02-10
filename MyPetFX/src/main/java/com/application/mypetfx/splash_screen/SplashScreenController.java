package com.application.mypetfx.splash_screen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SplashScreenController {

    @FXML
    private Label progress;

    @FXML
    private ProgressBar progressBar;

    public void setLabel(String text) {
        this.progress.setText(text);
    }

    public void setStatProgressBar(double progress) {
        this.progressBar.setProgress(progress);
    }

}
