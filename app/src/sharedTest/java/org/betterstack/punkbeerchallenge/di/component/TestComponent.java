package org.betterstack.punkbeerchallenge.di.component;

import org.betterstack.punkbeerchallenge.di.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {

}