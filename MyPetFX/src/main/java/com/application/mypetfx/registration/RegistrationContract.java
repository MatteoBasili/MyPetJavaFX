package com.application.mypetfx.registration;

import java.io.IOException;

public interface RegistrationContract {

    interface RegistrationListener {
        void onFailed(String message);

        void onSuccess();
    }

    interface RegistrationView {
        void hideProgressIndicator();

        void onFailed(String message);

        void onSuccess() throws IOException;

        void showProgressIndicator();
    }

}
