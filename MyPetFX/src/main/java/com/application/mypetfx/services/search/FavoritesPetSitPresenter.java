package com.application.mypetfx.services.search;

import com.application.mypetfx.services.search.data.FavoritesPetSitInteractor;

public class FavoritesPetSitPresenter implements PetSitterSearchContract.PetSitterFavoritesListener {

    private final FavoritesPetSitInteractor favoritesPetSitInteractor;
    private final PetSitterSearchContract.PetSitterFavoritesView petSitterFavoritesView;

    public FavoritesPetSitPresenter(PetSitterSearchContract.PetSitterFavoritesView petSitterFavoritesView) {
        this.petSitterFavoritesView = petSitterFavoritesView;
        this.favoritesPetSitInteractor = new FavoritesPetSitInteractor(this);
    }

    public void setFavorite(String user, String petSitter) {
        this.favoritesPetSitInteractor.setPetSitterToFavorites(user, petSitter);
    }

    public void onSetFavoriteSuccess() {
        this.petSitterFavoritesView.onSetFavoriteSuccess();
    }

    public void onSetFavoriteFailed(String message) {
        this.petSitterFavoritesView.onSetFavoriteFailed(message);
    }

}
