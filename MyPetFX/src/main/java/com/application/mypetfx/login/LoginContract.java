package com.application.mypetfx.login;

import java.io.IOException;
import java.util.prefs.BackingStoreException;

public interface LoginContract {

    interface LoginListener {
        void onFailed(String message);

        void onSuccess(int role) throws BackingStoreException, IOException, InterruptedException;
    }

    interface LoginView {
        void hideProgressIndicator();

        void onFailed(String message);

        void onSuccess(int role) throws BackingStoreException, IOException, InterruptedException;

        void showProgressIndicator();
    }

}
