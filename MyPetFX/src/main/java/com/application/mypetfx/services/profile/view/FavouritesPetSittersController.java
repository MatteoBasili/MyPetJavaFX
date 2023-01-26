package com.application.mypetfx.services.profile.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.profile.LoadFavPetSitPresenter;
import com.application.mypetfx.services.profile.ProfileContract;
import com.application.mypetfx.services.search.view.SinglePetSitterItemController;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FavouritesPetSittersController implements Initializable, ProfileContract.LoadFavPetView {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox itemHolder;
    @FXML
    private Label favoritesPets;
    @FXML
    private ProgressIndicator loadProgressIndicator;
    @FXML
    private Label noResultsText;

    private UserSingletonClass userSingletonClass;
    private LoadFavPetSitPresenter presenter;
    private Node[] items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        presenter = new LoadFavPetSitPresenter(this);
        favoritesPets.setOnMouseClicked(e -> showPets());
        loadFavPetSitters();
    }

    @FXML
    private void showPets() {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/favoritesPets.fxml");
    }

    private void loadFavPetSitters() {
        presenter.loadFavorites(userSingletonClass.getUsername());
    }

    @Override
    public void hideProgressIndicator() {
        loadProgressIndicator.setVisible(false);
    }

    @Override
    public void onLoadFavoritesFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    @Override
    public void onLoadFavoritesSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass) {

        petSitterResultsSingletonClass.setAnchorPane(anchorPane);

        if (petSitterResultsSingletonClass.getResultsNumber() > 0) {
            items = new Node[petSitterResultsSingletonClass.getResultsNumber()];
            for (int i = 0; i < items.length; i++) {
                final int j = i;

                setItem(petSitterResultsSingletonClass, i);

                items[i].setOnMouseEntered(event -> items[j].setStyle("-fx-background-color: #F5F5F5"));
                items[i].setOnMouseExited(event -> items[j].setStyle("-fx-background-color: white"));
                itemHolder.getChildren().add(items[i]);
            }
        } else {
            noResultsText.setVisible(true);
        }

    }

    @FXML
    private void setItem(PetSitterResultsSingletonClass results, int i) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/application/mypetfx/fxml/petSitSearchSingleItem.fxml")));
            items[i] = loader.load();
            SinglePetSitterItemController itemController = loader.getController();

            itemController.setListPosition(i);
            itemController.setSource(true);
            itemController.setPhoto(results.getPetSitProfileInfos().get(i).getImage());
            itemController.setUsername(results.getUsernames().get(i));
            itemController.setRegion(results.getRegions().get(i));
            itemController.setProvince(results.getProvinces().get(i));
            itemController.setLikes(results.getPetSitProfileInfos().get(i).getNumLikes());
            itemController.setDislikes(results.getPetSitProfileInfos().get(i).getNumDislikes());
            itemController.setFavorite(results.getFavorites().get(i));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
