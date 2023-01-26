package com.application.mypetfx.login;

import com.application.mypetfx.login.data.LoginCredentials;
import com.application.mypetfx.login.data.LoginInteractor;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

public class LoginPresenter implements LoginContract.LoginListener {

    private final LoginInteractor loginInteractor;
    private final LoginContract.LoginView loginView;

    public LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractor(this);
    }

    public void login(LoginCredentials credentials) {
        this.loginView.showProgressIndicator();
        this.loginInteractor.login(credentials);
    }

    public void onSuccess(int role) throws BackingStoreException, IOException, InterruptedException {
        this.loginView.hideProgressIndicator();
        this.loginView.onSuccess(role);
    }

    public void onFailed(String message) {
        this.loginView.hideProgressIndicator();
        this.loginView.onFailed(message);
    }

}
