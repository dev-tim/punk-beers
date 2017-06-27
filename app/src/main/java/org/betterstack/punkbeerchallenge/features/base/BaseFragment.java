package org.betterstack.punkbeerchallenge.features.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.betterstack.punkbeerchallenge.ApplicationStarter;
import org.betterstack.punkbeerchallenge.di.component.ConfigPersistentComponent;
import org.betterstack.punkbeerchallenge.di.component.DaggerConfigPersistentComponent;
import org.betterstack.punkbeerchallenge.di.component.FragmentComponent;
import org.betterstack.punkbeerchallenge.di.module.FragmentModule;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

    private static final String FRAGMENT_KEY_ID = "FRAGMENT_KEY_ID";
    private static final AtomicLong NEXT_ID_COUNTER = new AtomicLong(0);

    private static final LongSparseArray<ConfigPersistentComponent> componentsCache
            = new LongSparseArray<>();

    private long fragmentId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentId = restoreOrIncrementActivityId(savedInstanceState);

        ConfigPersistentComponent configPersistentComponent =
                reuseOrCreateConfigPersistentComponent();

        FragmentComponent fragmentComponent =
                configPersistentComponent.fragmentComponent(new FragmentModule(this));
        inject(fragmentComponent);
        attachView();
    }

    private long restoreOrIncrementActivityId(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getLong(FRAGMENT_KEY_ID);
        } else {
            return NEXT_ID_COUNTER.incrementAndGet();
        }
    }

    private ConfigPersistentComponent reuseOrCreateConfigPersistentComponent() {
        ConfigPersistentComponent configPersistentComponent;

        if (componentsCache.get(fragmentId) == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", fragmentId);

            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(ApplicationStarter.get(getActivity()).getAppComponent())
                    .build();

        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", fragmentId);
            configPersistentComponent = componentsCache.get(fragmentId);
        }

        return configPersistentComponent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayout();

    protected abstract void inject(FragmentComponent fragmentComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(FRAGMENT_KEY_ID, fragmentId);
    }

    @Override
    public void onDestroy() {

        // Checking if in the process of being destroyed in order to be recreated
        // with a new configuration.
        if (!getActivity().isChangingConfigurations()) {
            Timber.i("Removing ConfigPersistentComponent id=%d", fragmentId);
            componentsCache.remove(fragmentId);
        }

        detachPresenter();
        super.onDestroy();
    }
}
