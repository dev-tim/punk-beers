package org.betterstack.punkbeerchallenge.di.component;

import org.betterstack.punkbeerchallenge.di.annotations.FragmentSpecific;
import org.betterstack.punkbeerchallenge.di.module.FragmentModule;

import dagger.Subcomponent;

@FragmentSpecific
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {}