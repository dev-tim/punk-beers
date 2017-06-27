package org.betterstack.punkbeerchallenge.runner;

import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnitRunner;

import org.betterstack.punkbeerchallenge.util.RxIdlingExecutionHook;
import org.betterstack.punkbeerchallenge.util.RxIdlingResource;

import rx.plugins.RxJavaPlugins;

public class RxJUnitRunner extends AndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        RxIdlingResource rxIdlingResource = new RxIdlingResource();
        RxJavaPlugins.getInstance()
                .registerObservableExecutionHook(new RxIdlingExecutionHook(rxIdlingResource));
        Espresso.registerIdlingResources(rxIdlingResource);
    }

}
