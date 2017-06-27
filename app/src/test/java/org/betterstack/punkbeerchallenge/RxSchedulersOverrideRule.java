package org.betterstack.punkbeerchallenge;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersOverrideRule implements TestRule {

    public final Scheduler SCHEDULER_INSTANCE = Schedulers.trampoline();
    private Function<Scheduler, Scheduler> mSchedulerFunction = scheduler -> SCHEDULER_INSTANCE;
    private Function<Callable<Scheduler>, Scheduler> schedulerFunctionLazy =
            schedulerCallable -> SCHEDULER_INSTANCE;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(mSchedulerFunction);
                RxJavaPlugins.setNewThreadSchedulerHandler(mSchedulerFunction);
                RxJavaPlugins.setComputationSchedulerHandler(mSchedulerFunction);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
