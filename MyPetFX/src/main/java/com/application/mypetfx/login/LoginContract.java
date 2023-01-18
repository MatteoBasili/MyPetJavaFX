package com.application.mypetfx.login;

import java.util.prefs.BackingStoreException;

public interface LoginContract {

    interface LoginListener {
        void onFailed(String message);

        void onSuccess(int role) throws BackingStoreException;
    }

    interface LoginView {
        void hideProgressIndicator();

        void onFailed(String message);

        void onSuccess(int role) throws BackingStoreException;

        void showProgressIndicator();
    }

}
