package com.application.mypetfx.services.profile;

import com.application.mypetfx.registration.data.PetSitServices;
import com.application.mypetfx.services.profile.data.ServicesInteractor;

public class ServicesPresenter implements ProfileContract.ServicesListener {

    private final ServicesInteractor servicesInteractor;
    private final ProfileContract.ServicesView servicesView;


    public ServicesPresenter(ProfileContract.ServicesView servicesView) {
        this.servicesView = servicesView;
        this.servicesInteractor = new ServicesInteractor(this);
    }

    public void loadServices(String user) {
        this.servicesInteractor.loadServices(user);
    }

    public void saveServices(String user, PetSitServices petSitServices) {
        this.servicesView.showProgressIndicator();
        this.servicesInteractor.saveServices(user, petSitServices);
    }

    public void onLoadServicesSuccess(PetSitServices petSitServices) {
        this.servicesView.hideProgressIndicator();
        this.servicesView.onLoadServicesSuccess(petSitServices);
    }

    public void onLoadServicesFailed(String message) {
        this.servicesView.hideProgressIndicator();
        this.servicesView.onLoadServicesFailed(message);
    }

    @Override
    public void onStoreServicesFailed(String message) {
        this.servicesView.hideProgressIndicator();
        this.servicesView.onStoreServicesFailed(message);
    }

    @Override
    public void onStoreServicesSuccess() {
        this.servicesView.hideProgressIndicator();
        this.servicesView.onStoreServicesSuccess();
    }

}
