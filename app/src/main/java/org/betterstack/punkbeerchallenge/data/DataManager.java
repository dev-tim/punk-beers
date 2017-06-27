package org.betterstack.punkbeerchallenge.data;

import org.betterstack.punkbeerchallenge.data.api.service.PunkBeerService;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.repository.PunkBeerRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class DataManager {

    private PunkBeerService punkBeerService;

    private PunkBeerRepository punkBeerRepository;

    @Inject
    public DataManager(PunkBeerService punkBeerService, PunkBeerRepository punkBeerRepository) {
        this.punkBeerService = punkBeerService;
        this.punkBeerRepository = punkBeerRepository;
    }

    public Single<List<PunkBeer>> getPunkBeerList(int limit) {
        return punkBeerService.getPunkBeerList(limit);
    }

    public void insertOrUpdatePunkBeers(List<PunkBeer> punkBeers) {
        punkBeerRepository.insertOrUpdateAll(punkBeers);
    }

    public Observable<List<PunkBeer>> getLocalPunkBeerList() {
        return punkBeerRepository.findAll();
    }

    public Observable<List<PunkBeer>> getLocalPunkBeersMatchingName(String name) {
        return punkBeerRepository.findByName(name);
    }

    public Single<PunkBeer> getLocalPunkBeerById(Long punkBeerId) {
        return punkBeerRepository.findById(punkBeerId);
    }

}