package org.betterstack.punkbeerchallenge.data.model.realm;

import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.junit.Test;

import static org.junit.Assert.*;

public class RealmPunkBeerTest {

    @Test
    public void convertPunkBeerTest() throws Exception {
        RealmPunkBeer realmPunkBeer = TestData.generateRealmPunkBeer(1);
        PunkBeer punkBeer = realmPunkBeer.toPunkEntity();
        assertEquals(punkBeer.getId(), realmPunkBeer.getId());
        assertEquals(punkBeer.getName(), realmPunkBeer.getName());
        assertEquals(punkBeer.getTagline(), realmPunkBeer.getTagline());
        assertEquals(punkBeer.getAbv(), realmPunkBeer.getAbv());
        assertEquals(punkBeer.getImageUrl(), realmPunkBeer.getImageUrl());
    }

}