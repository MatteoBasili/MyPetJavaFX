package com.application.mypetfx.services.profile;

import com.application.mypetfx.services.profile.data.PersonalInfo;
import com.application.mypetfx.services.profile.data.PersonalInfoInteractor;

public class PersonalInfoPresenter implements ProfileContract.PersonalInfoListener {

    private final PersonalInfoInteractor personalInfoInteractor;
    private final ProfileContract.PersonalInfoView personalInfoView;


    public PersonalInfoPresenter(ProfileContract.PersonalInfoView personalInfoView) {
        this.personalInfoView = personalInfoView;
        this.personalInfoInteractor = new PersonalInfoInteractor(this);
    }

    public void loadInfo(String user) {
        this.personalInfoInteractor.loadPersonalInfo(user);
    }

    public void saveInfo(String user, PersonalInfo personalInfo) {
        this.personalInfoView.showProgressIndicator();
        this.personalInfoInteractor.savePersonalInfo(user, personalInfo);
    }

    public void onLoadInfoSuccess(PersonalInfo personalInfo) {
        this.personalInfoView.hideProgressIndicator();
        this.personalInfoView.onLoadInfoSuccess(personalInfo);
    }

    public void onLoadInfoFailed(String message) {
        this.personalInfoView.hideProgressIndicator();
        this.personalInfoView.onLoadInfoFailed(message);
    }

    @Override
    public void onStoreInfoFailed(String message) {
        this.personalInfoView.hideProgressIndicator();
        this.personalInfoView.onStoreInfoFailed(message);
    }

    @Override
    public void onStoreInfoSuccess() {
        this.personalInfoView.hideProgressIndicator();
        this.personalInfoView.onStoreInfoSuccess();
    }

}
