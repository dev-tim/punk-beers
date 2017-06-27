package org.betterstack.punkbeerchallenge.utils;

import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RepositoryUtilsTest {

    @Test
    public void whenReceivedPunkBeersShouldTransformToRealmPunkBeers() throws Exception {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        List<Long> punkBeersIds = punkBeers.stream().map(PunkBeer::getId).collect(Collectors.toList());
        List<Long> realmPunkBeersIds = RepositoryUtils.toRealmObjects(punkBeers).stream().map(RealmPunkBeer::getId).collect(Collectors.toList());;
        assertEquals(punkBeersIds, realmPunkBeersIds);
    }

    @Test
    public void whenReceivedRealmPunkBeersShouldTransformToPunkBeers() throws Exception {
        RealmPunkBeer realmPunkBeer = TestData.generateRealmPunkBeer(1);
        List<RealmPunkBeer> realmPunkBeers = new ArrayList<>();
        realmPunkBeers.add(realmPunkBeer);

        List<PunkBeer> realmPunkBeersIds = RepositoryUtils.fromRealmList(realmPunkBeers);;
        assertEquals(realmPunkBeer.getId(), realmPunkBeersIds.get(0).getId());
    }


}