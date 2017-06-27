package org.betterstack.punkbeerchallenge.di.module;

import org.betterstack.punkbeerchallenge.data.api.service.PunkBeerService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    PunkBeerService providePunkBeerService(Retrofit retrofit){
        return retrofit.create(PunkBeerService.class);
    }

}
