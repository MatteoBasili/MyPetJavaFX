package com.application.mypetfx.services.search.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PetSitterSearchResultsController implements Initializable {

    private static final Logger logger = Logger.getLogger(PetSitterSearchResultsController.class);

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox itemHolder;
    @FXML
    private ImageView back;
    @FXML
    private Label noResultsText;

    private PetSitterResultsSingletonClass results;
    private Node[] items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        results = PetSitterResultsSingletonClass.getSingletonInstance();
        results.setAnchorPane(anchorPane);

        back.setOnMouseClicked(e -> back());

        if (results.getResultsNumber() > 0) {
            items = new Node[results.getResultsNumber()];
            for (int i = 0; i < items.length; i++) {
                final int j = i;

                setItem(i);

                items[i].setOnMouseEntered(event -> items[j].setStyle("-fx-background-color: #F5F5F5"));
                items[i].setOnMouseExited(event -> items[j].setStyle("-fx-background-color: white"));
                itemHolder.getChildren().add(items[i]);
            }
        } else {
            noResultsText.setVisible(true);
        }

    }

    @FXML
    private void back(){
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitterSearchMenu.fxml");
    }

    @FXML
    private void setItem(int i) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/petSitSearchSingleItem.fxml")));
            items[i] = loader.load();
            SinglePetSitterItemController itemController = loader.getController();

            itemController.setListPosition(i);
            itemController.setSource(false);
            itemController.setPhoto(results.getPetSitProfileInfos().get(i).getImage());
            itemController.setUsername(results.getUsernames().get(i));
            itemController.setRegion(results.getRegions().get(i));
            itemController.setProvince(results.getProvinces().get(i));
            itemController.setLikes(results.getPetSitProfileInfos().get(i).getNumLikes());
            itemController.setDislikes(results.getPetSitProfileInfos().get(i).getNumDislikes());
            itemController.setFavorite(results.getFavorites().get(i));
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        }
    }

}
