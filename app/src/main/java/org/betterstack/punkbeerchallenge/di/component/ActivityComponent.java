package org.betterstack.punkbeerchallenge.di.component;

import org.betterstack.punkbeerchallenge.features.main.MainActivity;
import org.betterstack.punkbeerchallenge.di.annotations.ActivitySpecific;
import org.betterstack.punkbeerchallenge.di.module.ActivityModule;
import org.betterstack.punkbeerchallenge.features.webview.WebViewDetailsActivity;

import dagger.Subcomponent;

@ActivitySpecific
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(WebViewDetailsActivity mainActivity);

}