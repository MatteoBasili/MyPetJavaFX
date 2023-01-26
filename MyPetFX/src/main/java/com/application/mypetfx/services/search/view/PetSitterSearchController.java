package com.application.mypetfx.services.search.view;

import com.application.mypetfx.services.DashboardController;
import com.application.mypetfx.services.search.PetSitterSearchContract;
import com.application.mypetfx.services.search.PetSitterSearchPresenter;
import com.application.mypetfx.services.search.data.PetSitSearchFilters;
import com.application.mypetfx.utils.factory_method_example.ProvincesFactory;
import com.application.mypetfx.utils.factory_method_example.Regions;
import com.application.mypetfx.utils.factory_method_example.provinces.ProvincesListBase;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;
import com.application.mypetfx.utils.singleton_examples.UserSingletonClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PetSitterSearchController implements Initializable, PetSitterSearchContract.PetSitterSearchView {

    private static final Logger logger = Logger.getLogger(PetSitterSearchController.class);

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView back;
    @FXML
    private CheckBox catBox;
    @FXML
    private CheckBox dogBox;
    @FXML
    private Button findButton;
    @FXML
    private CheckBox otherPetsBox;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ComboBox<String> provincesBox;
    @FXML
    private ComboBox<String> regionsBox;

    private UserSingletonClass userSingletonClass;
    private ProvincesFactory provincesFactory;
    private PetSitterSearchPresenter presenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSingletonClass = UserSingletonClass.getSingletonInstance();
        presenter = new PetSitterSearchPresenter(this);
        initializeChoiceBoxes();

        back.setOnMouseClicked(e -> back());
        findButton.setOnAction(this::findResults);
    }

    private void initializeChoiceBoxes() {
        regionsBox.setPromptText("Select the region");
        provincesBox.setPromptText("First select the region");
        regionsBox.getItems().addAll(new Regions().createRegionList());
        provincesFactory = new ProvincesFactory();

        regionsBox.setOnAction(this::loadProvincesList);
    }

    @FXML
    private void loadProvincesList(ActionEvent event) {
        try{
            provincesBox.getItems().clear();
            provincesBox.setDisable(false);
            provincesBox.setPromptText("Select the province");
            ProvincesListBase provincesListBase = provincesFactory.createProvinceBaseList(regionsBox.getSelectionModel().getSelectedIndex() + 1);
            List<String> dynamicProvincesList = provincesListBase.createProvincesList();
            provincesBox.getItems().addAll(dynamicProvincesList);
        }
        catch (Exception e) {
            logger.error("Error: ", e);
        }
    }

    @FXML
    private void back(){
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/searchPage.fxml");
    }

    @FXML
    private void findResults(ActionEvent event) {
        findButton.setDisable(true);
        boolean dog = dogBox.isSelected();
        boolean cat = catBox.isSelected();
        boolean otherPets = otherPetsBox.isSelected();
        String regionInput = (regionsBox.getSelectionModel().getSelectedItem() == null) ? "" : regionsBox.getSelectionModel().getSelectedItem();
        String provinceInput = (provincesBox.getSelectionModel().getSelectedItem() == null) ? "" : provincesBox.getSelectionModel().getSelectedItem();

        PetSitSearchFilters petSitSearchFilters = new PetSitSearchFilters();
        petSitSearchFilters.setDog(dog);
        petSitSearchFilters.setCat(cat);
        petSitSearchFilters.setOtherPets(otherPets);
        petSitSearchFilters.setRegion(regionInput);
        petSitSearchFilters.setProvince(provinceInput);

        presenter.findPetSitters(userSingletonClass.getUsername(), petSitSearchFilters);
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void onFindResultsFailed(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
        findButton.setDisable(false);
    }

    @Override
    public void onFindResultsSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass) {
        anchorPane = DashboardController.changeScreen(anchorPane, "/com/application/mypetfx/fxml/petSitSearchResults.fxml");
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.setVisible(true);
        progressIndicator.requestFocus();
    }
}
