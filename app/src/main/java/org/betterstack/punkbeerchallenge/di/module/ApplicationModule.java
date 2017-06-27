package org.betterstack.punkbeerchallenge.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.betterstack.punkbeerchallenge.Constants;
import org.betterstack.punkbeerchallenge.di.annotations.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @ApplicationContext
    SharedPreferences provideSharedPreference(@ApplicationContext Context context) {
        return context.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
    }


}
