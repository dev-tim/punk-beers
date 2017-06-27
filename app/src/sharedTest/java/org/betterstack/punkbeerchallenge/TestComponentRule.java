package org.betterstack.punkbeerchallenge;

import android.content.Context;

import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.di.component.TestComponent;
import org.betterstack.punkbeerchallenge.di.module.ApplicationTestModule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.betterstack.punkbeerchallenge.di.component.DaggerTestComponent;

public class TestComponentRule implements TestRule {

    private final TestComponent testComponent;
    private final Context context;

    public TestComponentRule(Context context) {
        this.context = context;
        ApplicationStarter application = ApplicationStarter.get(context);
        testComponent =
                DaggerTestComponent.builder()
                        .applicationTestModule(new ApplicationTestModule(application))
                        .build();
    }

    public TestComponent getTestComponent() {
        return testComponent;
    }

    public Context getContext() {
        return context;
    }

    public DataManager getMockDataManager() {
        return testComponent.dataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                ApplicationStarter application = ApplicationStarter.get(context);
                application.setAppComponent(testComponent);
                base.evaluate();
                application.setAppComponent(null);
            }
        };
    }
}
