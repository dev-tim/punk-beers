package org.betterstack.punkbeerchallenge.di.component;

import org.betterstack.punkbeerchallenge.di.annotations.ConfigPersistentSpecific;
import org.betterstack.punkbeerchallenge.di.module.ActivityModule;
import org.betterstack.punkbeerchallenge.di.module.FragmentModule;

import dagger.Component;

@ConfigPersistentSpecific
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
