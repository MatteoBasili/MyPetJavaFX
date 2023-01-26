package com.application.mypetfx.pwd_recovery;

public interface PwdRecoveryContract {

    interface PWDRecoveryListener {
        void onFailed(String message);

        void onSuccess();
    }

    interface PWDRecoveryView {
        void hideProgressIndicator();

        void onFailed(String message);

        void onSuccess();

        void showProgressIndicator();
    }

}
