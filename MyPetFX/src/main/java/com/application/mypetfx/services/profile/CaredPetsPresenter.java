package com.application.mypetfx.services.profile;

import com.application.mypetfx.registration.data.PetSitCaredPets;
import com.application.mypetfx.services.profile.data.CaredPetsInteractor;

public class CaredPetsPresenter implements ProfileContract.CaredPetsListener {

    private final CaredPetsInteractor caredPetsInteractor;
    private final ProfileContract.CaredPetsView caredPetsView;


    public CaredPetsPresenter(ProfileContract.CaredPetsView caredPetsView) {
        this.caredPetsView = caredPetsView;
        this.caredPetsInteractor = new CaredPetsInteractor(this);
    }

    public void loadCaredPets(String user) {
        this.caredPetsInteractor.loadCaredPets(user);
    }

    public void saveCaredPets(String user, PetSitCaredPets petSitCaredPets) {
        this.caredPetsView.showProgressIndicator();
        this.caredPetsInteractor.saveCaredPets(user, petSitCaredPets);
    }

    public void onLoadPetsSuccess(PetSitCaredPets petSitCaredPets) {
        this.caredPetsView.hideProgressIndicator();
        this.caredPetsView.onLoadPetsSuccess(petSitCaredPets);
    }

    public void onLoadPetsFailed(String message) {
        this.caredPetsView.hideProgressIndicator();
        this.caredPetsView.onLoadPetsFailed(message);
    }

    @Override
    public void onStorePetsFailed(String message) {
        this.caredPetsView.hideProgressIndicator();
        this.caredPetsView.onStorePetsFailed(message);
    }

    @Override
    public void onStorePetsSuccess() {
        this.caredPetsView.hideProgressIndicator();
        this.caredPetsView.onStorePetsSuccess();
    }

}
