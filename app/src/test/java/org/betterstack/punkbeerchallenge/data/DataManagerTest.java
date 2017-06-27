package org.betterstack.punkbeerchallenge.data;

import org.betterstack.punkbeerchallenge.RxSchedulersOverrideRule;
import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.api.service.PunkBeerService;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.data.repository.PunkBeerRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    private PunkBeerService punkBeerService;

    @Mock
    private PunkBeerRepository punkBeerRepository;

    private DataManager dataManager;

    @Before
    public void setUp() {
        dataManager = new DataManager(punkBeerService, punkBeerRepository);
    }

    @Test
    public void getPunkBeerListCompletesAndEmitsPunkBeerList() {
        List<PunkBeer> punkBeerList = TestData.generatePunkBeerList(10);

        when(punkBeerService.getPunkBeerList(anyInt()))
                .thenReturn(Single.just(punkBeerList));

        dataManager
                .getPunkBeerList(10)
                .test()
                .assertComplete()
                .assertValue(punkBeerList);
    }

    @Test
    public void insertPunkBeersShouldCallInsertAllRepo() throws Exception {
        List<PunkBeer> punkBeerList = TestData.generatePunkBeerList(10);


        doNothing().when(punkBeerRepository).insertOrUpdateAll(refEq(punkBeerList));

        dataManager.insertOrUpdatePunkBeers(punkBeerList);
        verify(punkBeerRepository, timeout(1))
                .insertOrUpdateAll(refEq(punkBeerList));
    }

    @Test
    public void getLocalPunkBeerListCompletesAndReturnsPunkBeerList() {
        List<PunkBeer> punkBeerList = TestData.generatePunkBeerList(10);

        when(punkBeerRepository.findAll())
                .thenReturn(Observable.just(punkBeerList));

        dataManager
                .getLocalPunkBeerList()
                .test()
                .assertComplete()
                .assertValue(punkBeerList);
    }

    @Test
    public void getLocalPunkBeerByIdCompletesAndReturnsBeerList() {
        PunkBeer punkBeer = TestData.generatePunkBeer(42);

        when(punkBeerRepository.findById(anyLong()))
                .thenReturn(Single.just(punkBeer));

        dataManager
                .getLocalPunkBeerById(42L)
                .test()
                .assertComplete()
                .assertValue(punkBeer);
    }


    @Test
    public void getLocalPunkBeerMatchingNameCompletesAndReturnsBeerList() {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);

        when(punkBeerRepository.findByName(anyString()))
                .thenReturn(Observable.just(punkBeers));

        dataManager
                .getLocalPunkBeersMatchingName("sampleName")
                .test()
                .assertComplete()
                .assertValue(punkBeers);
    }

}