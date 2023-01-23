package com.application.mypetfx.services.profile;

import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;

public interface ProfileContract {

    interface PetSitProfileListener {
        void onLoadProfileFailed(String message);

        void onLoadProfileSuccess(LoadPetSitProfileInfo loadPetSitProfileInfo);

        void onStorePhotoFailed(String message);

        void onStorePhotoSuccess();
    }

    interface PetSitProfileView {
        void hideLoadProgressIndicator();

        void hideSaveProgressIndicator();

        void onLoadProfileFailed(String message);

        void onLoadProfileSuccess(LoadPetSitProfileInfo loadPetSitProfileInfo);

        void onStorePhotoFailed(String message);

        void onStorePhotoSuccess();

        void showSaveProgressIndicator();
    }

}
