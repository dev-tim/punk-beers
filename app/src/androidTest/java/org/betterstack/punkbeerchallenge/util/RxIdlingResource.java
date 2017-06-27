package org.betterstack.punkbeerchallenge.util;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

import timber.log.Timber;

/**
 * Idea is taken from here https://github.com/ReactiveX/RxAndroid/issues/149
 */
public class RxIdlingResource implements IdlingResource {

    private final AtomicInteger mActiveSubscriptionsCount = new AtomicInteger(0);
    private ResourceCallback mResourceCallback;

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return mActiveSubscriptionsCount.get() <= 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    public void incrementActiveSubscriptionsCount() {
        int count = mActiveSubscriptionsCount.incrementAndGet();
        Timber.i("Active subscriptions count increased to %d", count);
    }

    public void decrementActiveSubscriptionsCount() {
        int count = mActiveSubscriptionsCount.decrementAndGet();
        Timber.i("Active subscriptions count decreased to %d", count);
        if (isIdleNow()) {
            Timber.i("There is no active subscriptions, transitioning to Idle");
            mResourceCallback.onTransitionToIdle();
        }
    }
}

