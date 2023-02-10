package com.application.mypetfx.services.profile;

import com.application.mypetfx.registration.data.PetSitCaredPets;
import com.application.mypetfx.registration.data.PetSitServices;
import com.application.mypetfx.services.profile.data.PetSitProfileInfo;
import com.application.mypetfx.services.profile.data.PersonalInfo;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;

public interface ProfileContract {

    interface PetSitProfileListener {
        void onLoadProfileFailed(String message);

        void onLoadProfileSuccess(PetSitProfileInfo petSitProfileInfo);

        void onStorePhotoFailed(String message);

        void onStorePhotoSuccess();
    }

    interface PetSitProfileView {
        void hideLoadProgressIndicator();

        void hideSaveProgressIndicator();

        void onLoadProfileFailed(String message);

        void onLoadProfileSuccess(PetSitProfileInfo petSitProfileInfo);

        void onStorePhotoFailed(String message);

        void onStorePhotoSuccess();

        void showSaveProgressIndicator();
    }

    interface PersonalInfoListener {
        void onLoadInfoFailed(String message);

        void onLoadInfoSuccess(PersonalInfo personalInfo);

        void onStoreInfoFailed(String message);

        void onStoreInfoSuccess();
    }

    interface PersonalInfoView {
        void hideProgressIndicator();

        void onLoadInfoFailed(String message);

        void onLoadInfoSuccess(PersonalInfo personalInfo);

        void onStoreInfoFailed(String message);

        void onStoreInfoSuccess();

        void showProgressIndicator();
    }

    interface CaredPetsListener {
        void onLoadPetsFailed(String message);

        void onLoadPetsSuccess(PetSitCaredPets petSitCaredPets);

        void onStorePetsFailed(String message);

        void onStorePetsSuccess();
    }

    interface CaredPetsView {
        void hideProgressIndicator();

        void onLoadPetsFailed(String message);

        void onLoadPetsSuccess(PetSitCaredPets petSitCaredPets);

        void onStorePetsFailed(String message);

        void onStorePetsSuccess();

        void showProgressIndicator();
    }

    interface ServicesListener {
        void onLoadServicesFailed(String message);

        void onLoadServicesSuccess(PetSitServices petSitServices);

        void onStoreServicesFailed(String message);

        void onStoreServicesSuccess();
    }

    interface ServicesView {
        void hideProgressIndicator();

        void onLoadServicesFailed(String message);

        void onLoadServicesSuccess(PetSitServices petSitServices);

        void onStoreServicesFailed(String message);

        void onStoreServicesSuccess();

        void showProgressIndicator();
    }

    interface LoadFavPetSitListener {

        void onLoadFavoritesFailed(String message);

        void onLoadFavoritesSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass);

    }

    interface LoadFavPetView {

        void hideProgressIndicator();

        void onLoadFavoritesFailed(String message);

        void onLoadFavoritesSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass);

    }

}
