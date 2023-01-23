package com.application.mypetfx.services.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchMenuController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button.setOnAction(this::handleButton);
    }

    @FXML
    private void handleButton(ActionEvent event) {
        label.setVisible(true);
    }
}
