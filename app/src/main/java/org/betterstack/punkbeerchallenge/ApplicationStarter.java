package org.betterstack.punkbeerchallenge;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import org.betterstack.punkbeerchallenge.di.component.AppComponent;
import org.betterstack.punkbeerchallenge.di.component.DaggerAppComponent;
import org.betterstack.punkbeerchallenge.di.module.ApplicationModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class ApplicationStarter extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        if (BuildConfig.DEBUG) {
            // init logging
            Timber.plant(new Timber.DebugTree());

            // init debugger
            Stetho.initializeWithDefaults(this);

            // init leak detection
            LeakCanary.install(this);
        }
    }

    public static ApplicationStarter get(Context context) {
        return (ApplicationStarter) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return appComponent;
    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    // Use only for testing!
    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

}
