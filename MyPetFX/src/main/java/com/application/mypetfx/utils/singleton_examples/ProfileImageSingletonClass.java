package com.application.mypetfx.utils.singleton_examples;

import javafx.scene.image.Image;

public class ProfileImageSingletonClass {

    private static ProfileImageSingletonClass instance = null;

    private Image image;

    protected ProfileImageSingletonClass() {

    }

    public static synchronized ProfileImageSingletonClass getSingletonInstance() {
        if (ProfileImageSingletonClass.instance == null)
            ProfileImageSingletonClass.instance = new ProfileImageSingletonClass();
        return instance;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
