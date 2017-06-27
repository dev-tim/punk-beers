package org.betterstack.punkbeerchallenge.data.model.entity;

import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;
import org.junit.Test;

import static org.junit.Assert.*;

public class PunkBeerTest {

    @Test
    public void convertRealmPunkBeerTest() throws Exception {
        PunkBeer punkBeer = TestData.generatePunkBeer(1);
        RealmPunkBeer realmPunkBeer = punkBeer.toRealmObject();
        assertEquals(realmPunkBeer.getId(), punkBeer.getId());
        assertEquals(realmPunkBeer.getName(), punkBeer.getName());
        assertEquals(realmPunkBeer.getTagline(), punkBeer.getTagline());
        assertEquals(realmPunkBeer.getAbv(), punkBeer.getAbv());
        assertEquals(realmPunkBeer.getImageUrl(), punkBeer.getImageUrl());
    }

}