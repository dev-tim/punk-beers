package org.betterstack.punkbeerchallenge;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestData {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static PunkBeer generatePunkBeer(Integer id) {
        PunkBeer punkBeer = new PunkBeer();
        punkBeer.setId(id.longValue());
        punkBeer.setName(randomUuid() + id);
        punkBeer.setTagline("TagLine" + id);
        punkBeer.setAbv(42.0);
        punkBeer.setImageUrl("imageUrl" + id);

        return punkBeer;
    }

    public static RealmPunkBeer generateRealmPunkBeer(Integer id) {
        RealmPunkBeer realmPunkBeer = new RealmPunkBeer();
        realmPunkBeer.setId(id.longValue());
        realmPunkBeer.setName(randomUuid() + id);
        realmPunkBeer.setTagline("TagLine" + id);
        realmPunkBeer.setAbv(42.0);
        realmPunkBeer.setImageUrl("imageUrl" + id);

        return realmPunkBeer;
    }

    public static List<PunkBeer> generatePunkBeerList(int count) {
        List<PunkBeer> punkBeers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            punkBeers.add(generatePunkBeer(i));
        }
        return punkBeers;
    }

}
