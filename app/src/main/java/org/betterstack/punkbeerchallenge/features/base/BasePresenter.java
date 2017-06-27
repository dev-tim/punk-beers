package org.betterstack.punkbeerchallenge.features.base;

import org.betterstack.punkbeerchallenge.features.MVPView;
import org.betterstack.punkbeerchallenge.features.Presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Usage of CompositeDisposable is inspired by a sample in Fast Android networking library.
 * RxJava2 provides special container abstraction for observables, so we can use it for our
 * base Presenter.
 *
 * see https://github.com/amitshekhariitbhu/Fast-Android-Networking/blob/master/rx2sampleapp/src/main/java/com/rx2sampleapp/SubscriptionActivity.java
 */
public class BasePresenter<V extends MVPView> implements Presenter<V> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    @Override
    public void detachView() {
        this.view = null;
        compositeDisposable.clear();
    }

    public boolean isViewAttached() {
        return this.view != null;
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

}
