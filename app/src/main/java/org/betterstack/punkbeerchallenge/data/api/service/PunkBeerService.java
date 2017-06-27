package org.betterstack.punkbeerchallenge.data.api.service;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PunkBeerService {

    @GET("beers")
    Single<List<PunkBeer>> getPunkBeerList(@Query("per_page") int limit);

}
