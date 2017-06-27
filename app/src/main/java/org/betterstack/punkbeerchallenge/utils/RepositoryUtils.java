package org.betterstack.punkbeerchallenge.utils;


import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUtils {

    public static List<RealmPunkBeer> toRealmObjects(List<PunkBeer> punkBeerList){
        final List<RealmPunkBeer> realmPunkBeers = new ArrayList<>();
        for (PunkBeer p: punkBeerList){
            realmPunkBeers.add(p.toRealmObject());
        }
        return realmPunkBeers;
    }

    public static List<PunkBeer> fromRealmList(List<RealmPunkBeer> realmPunkBeers){
        List<PunkBeer> models = new ArrayList<>();
        for (RealmPunkBeer rp: realmPunkBeers){
            models.add(rp.toPunkEntity());
        }
        return models;
    }

}
