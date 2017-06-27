package org.betterstack.punkbeerchallenge.di.component;

import android.app.Application;
import android.content.Context;

import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.di.annotations.ApplicationContext;
import org.betterstack.punkbeerchallenge.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();
}

