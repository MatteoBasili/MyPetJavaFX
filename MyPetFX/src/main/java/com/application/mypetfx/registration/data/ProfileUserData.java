package com.application.mypetfx.registration.data;

public class ProfileUserData {

    private String address;
    private String name;
    private String phoneNumb;
    private String province;
    private String region;
    private String surname;

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getRegion() {
        return this.region;
    }

    public String getProvince() {
        return this.province;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumb() {
        return this.phoneNumb;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
