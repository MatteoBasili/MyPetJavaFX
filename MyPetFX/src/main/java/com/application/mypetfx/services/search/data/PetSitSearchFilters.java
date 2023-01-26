package com.application.mypetfx.services.search.data;

public class PetSitSearchFilters {

    private boolean dog;
    private boolean cat;
    private boolean otherPets;
    private String region;
    private String province;

    public boolean isDog() {
        return dog;
    }

    public void setDog(boolean dog) {
        this.dog = dog;
    }

    public boolean isCat() {
        return cat;
    }

    public void setCat(boolean cat) {
        this.cat = cat;
    }

    public boolean isOtherPets() {
        return otherPets;
    }

    public void setOtherPets(boolean otherPets) {
        this.otherPets = otherPets;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
