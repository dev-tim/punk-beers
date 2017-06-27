package org.betterstack.punkbeerchallenge.data.repository;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;
import org.betterstack.punkbeerchallenge.utils.RepositoryUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

@Singleton
public class PunkBeerRepository {

    @Inject
    public PunkBeerRepository() {
    }

    public void insertOrUpdateAll(final List<PunkBeer> punkBeerList) {
        try (Realm instance = Realm.getDefaultInstance()) {
            instance.executeTransactionAsync(r -> {
                List<RealmPunkBeer> objects = RepositoryUtils.toRealmObjects(punkBeerList);
                r.copyToRealmOrUpdate(objects);
            });
            Timber.i("Successfully inserted %d punk beers", punkBeerList.size());
        } catch (Throwable e) {
            Timber.e(e, "Failed to insert %d punk beers", punkBeerList.size());
        }
    }

    public Observable<List<PunkBeer>> findAll() {
        try (Realm instance = Realm.getDefaultInstance()) {
            RealmResults<RealmPunkBeer> allAsync = instance.where(RealmPunkBeer.class).findAll();
            Timber.i("Successfully fetched %d punk beers", allAsync.size());
            return Observable.just(RepositoryUtils.fromRealmList(allAsync));
        } catch (Throwable e) {
            Timber.e(e, "Failed to fetch %d punk beers");
            return Observable.error(e);
        }
    }

    public Single<PunkBeer> findById(Long punkBeerId) {
        try (Realm instance = Realm.getDefaultInstance()) {
            RealmPunkBeer byId = instance.where(RealmPunkBeer.class)
                    .equalTo("id", punkBeerId)
                    .findFirst();

            Timber.i("Successfully fetched punk beer by id %d", punkBeerId);
            return Single.just(byId.toPunkEntity());
        } catch (Throwable e) {
            Timber.e(e, "Failed to fetch punk beer by id %d", punkBeerId);
            return Single.error(e);
        }
    }

    public Observable<List<PunkBeer>> findByName(String name) {
        try (Realm instance = Realm.getDefaultInstance()) {
            RealmResults<RealmPunkBeer> byName = instance.where(RealmPunkBeer.class)
                    .contains("name", name, Case.INSENSITIVE)
                    .findAll();

            List<PunkBeer> models = new ArrayList<>();
            for (RealmPunkBeer rp : byName) {
                models.add(rp.toPunkEntity());
            }

            Timber.i("Successfully fetched %d punk beers", byName.size());
            return Observable.just(models);
        } catch (Throwable e) {
            Timber.e(e, "Failed to fetch %d punk beers");
            return Observable.error(e);
        }
    }
}
