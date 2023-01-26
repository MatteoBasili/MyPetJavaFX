package com.application.mypetfx.utils.singleton_examples;

public class PetSitterSingletonClass {

    private static PetSitterSingletonClass instance = null;

    private String username;
    private int positionInList;
    private boolean isFromFavorites;

    protected PetSitterSingletonClass() {

    }

    public static synchronized PetSitterSingletonClass getSingletonInstance() {
        if (PetSitterSingletonClass.instance == null)
            PetSitterSingletonClass.instance = new PetSitterSingletonClass();
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public boolean isFromFavorites() {
        return isFromFavorites;
    }

    public void setFromFavorites(boolean fromFavorites) {
        isFromFavorites = fromFavorites;
    }
}
