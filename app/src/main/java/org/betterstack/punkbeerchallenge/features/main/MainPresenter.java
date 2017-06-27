package org.betterstack.punkbeerchallenge.features.main;

import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.features.base.BasePresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainMVPView> {

    private DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getLocalPunkBeers() {
        Timber.i("Requested punk beers for main view from local cache");

        getView().showLoadingProgress(true);
        dataManager
                .getLocalPunkBeerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(p -> p.isEmpty() ? Observable.error(new Throwable("Empty list")) : Observable.just(p))
                .first(Collections.emptyList())
                .subscribe(
                        punkBeers -> {
                            Timber.i("Received %d punk beers from local cache", punkBeers.size());
                            getView().showLoadingProgress(false);
                            getView().showPunkBeers(punkBeers);
                        },
                        throwable -> {
                            Timber.e("Failed to receive punk beers from local cache", throwable);
                            getView().showLoadingProgress(false);
                            getView().showError(throwable);
                        });
    }

    public void getPunkBeers(int limit) {
        Timber.i("Requested punk beers for main view from API");
        getView().showLoadingProgress(true);

        Single<List<PunkBeer>> localPunkBeerSteram = prepareLocalPunkBeerStream();

        dataManager
                .getPunkBeerList(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(punkBeers -> {
                    Timber.i("Cache punk beers from API to local storage");
                    dataManager.insertOrUpdatePunkBeers(punkBeers);
                })
                .onErrorResumeNext(e -> {
                    Timber.w("Recovering local beer list, fetch punk beer from the local list");
                    return localPunkBeerSteram;
                })
                .flatMap(p -> p.isEmpty() ? Single.error(new Throwable("Empty list")) : Single.just(p))
                .subscribe(
                        punkBeers -> {
                            Timber.i("Received %d punk beers", punkBeers.size());
                            getView().showLoadingProgress(false);
                            getView().showPunkBeers(punkBeers);
                        },
                        throwable -> {
                            Timber.e(throwable, "Failed to receive punk beers from API");

                            getView().showLoadingProgress(false);
                            getView().showError(throwable);
                        });
    }

    private Single<List<PunkBeer>> prepareLocalPunkBeerStream() {
        // TODO Realm-related gotcha. RealmObjets can't be passed across different threads
        // we have to make a copy of the object to use io scheduler.
        //
        // See more: https://github.com/realm/realm-java/issues/1208

        return dataManager.getLocalPunkBeerList().first(Collections.emptyList());
    }

    public void submitSearchQuery(String searchTerm) {
        Timber.i("Searching term %s", searchTerm);

        dataManager.getLocalPunkBeersMatchingName(searchTerm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first(Collections.emptyList())
                .subscribe(matchingBeers -> {
                    Timber.i("Found matching %d punk beers", matchingBeers.size());
                    getView().showPunkBeers(matchingBeers);
                }, throwable -> {
                    Timber.e(throwable, "Failed to find matching punk beers");
                    getView().showPunkBeers(Collections.emptyList());
                });
    }

    public void updateSearch(String searchTerm) {
        Timber.i("Updating searching term %s", searchTerm);
        submitSearchQuery(searchTerm);
    }
}
