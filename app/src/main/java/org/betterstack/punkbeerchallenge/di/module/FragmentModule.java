package org.betterstack.punkbeerchallenge.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.betterstack.punkbeerchallenge.di.annotations.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return fragment;
    }

    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return fragment.getActivity();
    }
}
