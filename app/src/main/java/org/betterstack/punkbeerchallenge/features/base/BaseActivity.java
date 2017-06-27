package org.betterstack.punkbeerchallenge.features.base;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.betterstack.punkbeerchallenge.ApplicationStarter;
import org.betterstack.punkbeerchallenge.di.component.ActivityComponent;
import org.betterstack.punkbeerchallenge.di.component.ConfigPersistentComponent;
import org.betterstack.punkbeerchallenge.di.component.DaggerConfigPersistentComponent;
import org.betterstack.punkbeerchallenge.di.module.ActivityModule;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String ACTIVITY_KEY_ID = "ACTIVITY_KEY_ID";
    private static final AtomicLong NEXT_ID_COUNTER = new AtomicLong(0);

    // Sparse arrays are cheaper memory-wise, see - https://stackoverflow.com/a/31413003/1271136
    private static final LongSparseArray<ConfigPersistentComponent> componentsCache =
            new LongSparseArray<>();

    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("Init activity");
        setContentView(getLayout());
        ButterKnife.bind(this);
        Timber.i("Bound butter knife");

        // here we try to reuse components instances between configuration changes
        activityId = restoreOrIncrementActivityId(savedInstanceState);

        ConfigPersistentComponent component = reuseOrCreateConfigPersistentComponent();
        ActivityComponent activityComponent =
                component.activityComponent(new ActivityModule(this));

        Timber.i("Injected component");
        inject(activityComponent);
        attachView();
    }

    private long restoreOrIncrementActivityId(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState.getLong(ACTIVITY_KEY_ID);
        } else {
            return NEXT_ID_COUNTER.incrementAndGet();
        }
    }

    private ConfigPersistentComponent reuseOrCreateConfigPersistentComponent() {
        ConfigPersistentComponent configPersistentComponent;
        if (componentsCache.get(activityId) == null) {
            Timber.i("Not found cached, creating new ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(ApplicationStarter.get(this).getAppComponent())
                            .build();
            componentsCache.put(activityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent = componentsCache.get(activityId);
        }
        return configPersistentComponent;
    }

    protected abstract int getLayout();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ACTIVITY_KEY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Removing ConfigPersistentComponent with id=%d", activityId);
            componentsCache.remove(activityId);
        }
        detachPresenter();
        super.onDestroy();
    }

}
