package com.application.mypetfx.services.profile;

import com.application.mypetfx.services.profile.data.PetSitProfileInfo;
import com.application.mypetfx.services.profile.data.PetSitProfileInteractor;
import javafx.scene.image.Image;

public class PetSitProfilePresenter implements ProfileContract.PetSitProfileListener {

    private final PetSitProfileInteractor petSitProfileInteractor;
    private final ProfileContract.PetSitProfileView petSitProfileView;


    public PetSitProfilePresenter(ProfileContract.PetSitProfileView petSitProfileView) {
        this.petSitProfileView = petSitProfileView;
        this.petSitProfileInteractor = new PetSitProfileInteractor(this);
    }

    public void loadProfile(String user) {
        this.petSitProfileInteractor.loadProfile(user);
    }

    public void savePhoto(String user, Image image) {
        this.petSitProfileView.showSaveProgressIndicator();
        this.petSitProfileInteractor.savePhoto(user, image);
    }

    public void onLoadProfileSuccess(PetSitProfileInfo petSitProfileInfo) {
        this.petSitProfileView.hideLoadProgressIndicator();
        this.petSitProfileView.onLoadProfileSuccess(petSitProfileInfo);
    }

    public void onLoadProfileFailed(String message) {
        this.petSitProfileView.hideLoadProgressIndicator();
        this.petSitProfileView.onLoadProfileFailed(message);
    }

    @Override
    public void onStorePhotoFailed(String message) {
        this.petSitProfileView.hideSaveProgressIndicator();
        this.petSitProfileView.onStorePhotoFailed(message);
    }

    @Override
    public void onStorePhotoSuccess() {
        this.petSitProfileView.hideSaveProgressIndicator();
        this.petSitProfileView.onStorePhotoSuccess();
    }
}
