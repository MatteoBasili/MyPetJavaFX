package com.application.mypetfx.registration;

import com.application.mypetfx.registration.data.RegistrationCredentials;
import com.application.mypetfx.registration.data.RegistrationInteractor;

import java.io.IOException;

public class RegistrationPresenter implements RegistrationContract.RegistrationListener {

    private final RegistrationInteractor registrationInteractor;
    private final RegistrationContract.RegistrationView registrationView;


    public RegistrationPresenter(RegistrationContract.RegistrationView registrationView) {
        this.registrationView = registrationView;
        this.registrationInteractor = new RegistrationInteractor(this);
    }

    public void registerUser(RegistrationCredentials credentials) {
        this.registrationView.showProgressIndicator();
        this.registrationInteractor.registerAccount(credentials);
    }

    public void onSuccess() throws IOException {
        this.registrationView.hideProgressIndicator();
        this.registrationView.onSuccess();
    }

    public void onFailed(String message) {
        this.registrationView.hideProgressIndicator();
        this.registrationView.onFailed(message);
    }

}
