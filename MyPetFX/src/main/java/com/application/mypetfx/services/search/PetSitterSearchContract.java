package com.application.mypetfx.services.search;

import com.application.mypetfx.services.search.data.PetSitterDetails;
import com.application.mypetfx.utils.singleton_examples.PetSitterResultsSingletonClass;

public interface PetSitterSearchContract {

    interface PetSitterSearchListener {

        void onFindResultsFailed(String message);

        void onFindResultsSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass);
    }

    interface PetSitterSearchView {

        void hideProgressIndicator();

        void onFindResultsFailed(String message);

        void onFindResultsSuccess(PetSitterResultsSingletonClass petSitterResultsSingletonClass);

        void showProgressIndicator();
    }

    interface PetSitterFavoritesListener {

        void onSetFavoriteFailed(String message);

        void onSetFavoriteSuccess();
    }

    interface PetSitterFavoritesView {

        void onSetFavoriteFailed(String message);

        void onSetFavoriteSuccess();

    }

    interface PetSitterRatingListener {

        void onRateFailed(String message);

        void onRateSuccess();
    }

    interface PetSitterRatingView {

        void onRateFailed(String message);

        void onRateSuccess();

    }

    interface PetSitterDetailsListener {

        void onLoadDetailsFailed(String message);

        void onLoadDetailsSuccess(PetSitterDetails petSitterDetails);
    }

    interface PetSitterDetailsView {

        void hideProgressIndicator();

        void showProgressIndicator();

        void onLoadDetailsFailed(String message);

        void onLoadDetailsSuccess(PetSitterDetails petSitterDetails);

    }

}
