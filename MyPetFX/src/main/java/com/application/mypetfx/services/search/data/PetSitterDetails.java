package com.application.mypetfx.services.search.data;

import com.application.mypetfx.registration.data.PetSitCaredPets;
import com.application.mypetfx.registration.data.PetSitServices;
import com.application.mypetfx.registration.data.ProfileUserData;
import com.application.mypetfx.services.profile.data.LoadPetSitProfileInfo;

public class PetSitterDetails {

    private PetSitCaredPets petSitCaredPets;
    private PetSitServices petSitServices;
    private ProfileUserData profileUserData;
    private LoadPetSitProfileInfo loadPetSitProfileInfo;
    private PetSitterRating petSitterRating;
    private String email;

    public PetSitCaredPets getPetSitCaredPets() {
        return petSitCaredPets;
    }

    public void setPetSitCaredPets(PetSitCaredPets petSitCaredPets) {
        this.petSitCaredPets = petSitCaredPets;
    }

    public PetSitServices getPetSitServices() {
        return petSitServices;
    }

    public void setPetSitServices(PetSitServices petSitServices) {
        this.petSitServices = petSitServices;
    }

    public ProfileUserData getProfileUserData() {
        return profileUserData;
    }

    public void setProfileUserData(ProfileUserData profileUserData) {
        this.profileUserData = profileUserData;
    }

    public LoadPetSitProfileInfo getLoadPetSitProfileInfo() {
        return loadPetSitProfileInfo;
    }

    public void setLoadPetSitProfileInfo(LoadPetSitProfileInfo loadPetSitProfileInfo) {
        this.loadPetSitProfileInfo = loadPetSitProfileInfo;
    }

    public PetSitterRating getPetSitterRating() {
        return petSitterRating;
    }

    public void setPetSitterRating(PetSitterRating petSitterRating) {
        this.petSitterRating = petSitterRating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
