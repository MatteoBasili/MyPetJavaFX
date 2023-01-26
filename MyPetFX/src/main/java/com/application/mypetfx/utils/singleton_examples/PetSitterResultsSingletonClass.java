package com.application.mypetfx.utils.singleton_examples;

import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class PetSitterResultsSingletonClass {

    private static PetSitterResultsSingletonClass instance = null;

    private List<LoadPetSitProfileInfo> petSitProfileInfos;
    private List<String> usernames;
    private List<String> provinces;
    private List<Boolean> favorites;
    private List<String> regions;
    private AnchorPane anchorPane;

    public PetSitterResultsSingletonClass() {
        // requires empty constructor
    }

    public static synchronized PetSitterResultsSingletonClass getSingletonInstance() {
        if (PetSitterResultsSingletonClass.instance == null)
            PetSitterResultsSingletonClass.instance = new PetSitterResultsSingletonClass();
        return instance;
    }

    public List<LoadPetSitProfileInfo> getPetSitProfileInfos() {
        return petSitProfileInfos;
    }

    public void setPetSitProfileInfos(List<LoadPetSitProfileInfo> petSitProfileInfos) {
        this.petSitProfileInfos = petSitProfileInfos;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public List<Boolean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Boolean> favorites) {
        this.favorites = favorites;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public int getResultsNumber() {
        return getUsernames().size();
    }
}
