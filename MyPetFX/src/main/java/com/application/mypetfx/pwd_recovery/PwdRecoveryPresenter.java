package com.application.mypetfx.pwd_recovery;

import com.application.mypetfx.pwd_recovery.data.PwdRecoveryCredentials;
import com.application.mypetfx.pwd_recovery.data.PwdRecoveryInteractor;

public class PwdRecoveryPresenter implements PwdRecoveryContract.PWDRecoveryListener {

    private final PwdRecoveryInteractor pwdRecoveryInteractor;
    private final PwdRecoveryContract.PWDRecoveryView pwdRecoveryView;


    public PwdRecoveryPresenter(PwdRecoveryContract.PWDRecoveryView pwdRecoveryView) {
        this.pwdRecoveryView = pwdRecoveryView;
        this.pwdRecoveryInteractor = new PwdRecoveryInteractor(this);
    }

    public void recoverPassword(PwdRecoveryCredentials credentials) {
        this.pwdRecoveryView.showProgressIndicator();
        this.pwdRecoveryInteractor.recoverPassword(credentials);
    }

    public void onSuccess() {
        this.pwdRecoveryView.hideProgressIndicator();
        this.pwdRecoveryView.onSuccess();
    }

    public void onFailed(String message) {
        this.pwdRecoveryView.hideProgressIndicator();
        this.pwdRecoveryView.onFailed(message);
    }

}
