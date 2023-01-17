package com.application.mypetfx.splash_screen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label progress;

    public static Label label;

    @FXML
    private ProgressBar progressBar;

    public static ProgressBar statProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        label = progress;
        statProgressBar = progressBar;

    }

}
