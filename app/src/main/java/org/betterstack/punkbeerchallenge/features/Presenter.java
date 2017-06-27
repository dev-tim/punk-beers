package org.betterstack.punkbeerchallenge.features;

public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();

}
