package org.betterstack.punkbeerchallenge.features.webview;

import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.features.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

public class WebViewDetailsPresenter extends BasePresenter<WebViewDetailsMVPView> {


    private DataManager dataManager;

    @Inject
    public WebViewDetailsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getPunkBeerById(Long punkBeerId) {
        Timber.i("Requested punk beer from local cache %d", punkBeerId);

        dataManager.getLocalPunkBeerById(punkBeerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        punkBeer -> getView().showSelectedPunkBeer(punkBeer),
                        error -> getView().showError(error)
                );

    }
}
