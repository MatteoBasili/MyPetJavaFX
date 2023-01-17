package com.application.mypetfx.login;

public interface LoginContract {

    interface LoginListener {
        void onFailed(String str);

        void onSuccess(int i);
    }

    interface LoginView {
        void hideProgressbar();

        void onFailed(String str);

        void onSuccess(int i);

        void showProgressbar();
    }

}
