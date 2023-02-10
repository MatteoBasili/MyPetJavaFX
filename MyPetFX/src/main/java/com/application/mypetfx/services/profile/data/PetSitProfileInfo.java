package com.application.mypetfx.services.profile.data;

import javafx.scene.image.Image;

public class PetSitProfileInfo {

    private Image image;
    private int numDislikes;
    private int numLikes;

    public Image getImage() {
        return this.image;
    }

    public int getNumLikes() {
        return this.numLikes;
    }

    public int getNumDislikes() {
        return this.numDislikes;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setNumDislikes(int numDislikes) {
        this.numDislikes = numDislikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}
