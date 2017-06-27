package org.betterstack.punkbeerchallenge.di.module;

import android.app.Application;
import android.content.Context;

import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.data.api.service.PunkBeerService;
import org.betterstack.punkbeerchallenge.data.repository.PunkBeerRepository;
import org.betterstack.punkbeerchallenge.di.annotations.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class ApplicationTestModule {
    private final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    PunkBeerService providePunkBeerService() {
        return mock(PunkBeerService.class);
    }

    @Provides
    @Singleton
    PunkBeerRepository providePunkBeerRepository() {
        return mock(PunkBeerRepository.class);
    }

}

