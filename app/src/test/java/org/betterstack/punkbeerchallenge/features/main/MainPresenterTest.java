package org.betterstack.punkbeerchallenge.features.main;

import org.betterstack.punkbeerchallenge.RxSchedulersOverrideRule;
import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.junit.After;
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
public class MainPresenterTest {

    @Mock
    private MainMVPView mockMainMVPView;

    @Mock
    private DataManager mockDataManager;

    private MainPresenter mainPresenter;

    @Rule
    public final RxSchedulersOverrideRule customSchedulersRule = new RxSchedulersOverrideRule();


    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(mockDataManager);
        mainPresenter.attachView(mockMainMVPView);

        doNothing().when(mockDataManager).insertOrUpdatePunkBeers(anyListOf(PunkBeer.class));
    }

    @After
    public void tearDown() {
        mainPresenter.detachView();
    }

    @Test
    public void whenReceivedPunkBeersShouldShowPunkBeers() {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getPunkBeerList(10))
                .thenReturn(Single.just(punkBeers));
        when(mockDataManager.getLocalPunkBeerList()).thenReturn(Observable.never());


        mainPresenter.getPunkBeers(10);
        verify(mockMainMVPView, times(1))
                .showPunkBeers(punkBeers);
    }

    @Test
    public void whenReceivedPunkBeersShouldNeverShowError() {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getPunkBeerList(10))
                .thenReturn(Single.just(punkBeers));
        when(mockDataManager.getLocalPunkBeerList()).thenReturn(Observable.never());



        mainPresenter.getPunkBeers(10);
        verify(mockMainMVPView, never()).showError(any(Throwable.class));
    }

    @Test
    public void whenReceivedPunkBeersShouldDisableProgressView() {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getPunkBeerList(10))
                .thenReturn(Single.just(punkBeers));
        when(mockDataManager.getLocalPunkBeerList()).thenReturn(Observable.never());

        mainPresenter.getPunkBeers(10);
        verify(mockMainMVPView, times(1))
                .showLoadingProgress(false);
    }


    @Test
    public void whenFailedAPICallShouldReceivePunkBeersFromLocalCache() {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getPunkBeerList(10))
                .thenReturn(Single.error(new Throwable("Failed to fetch data!")));
        when(mockDataManager.getLocalPunkBeerList()).thenReturn(Observable.just(punkBeers));

        mainPresenter.getPunkBeers(10);
        verify(mockMainMVPView, times(1))
                .showPunkBeers(punkBeers);
    }


    @Test
    public void whenReceivedPunkBeerMatchingByNameShouldShowPunkBeers() throws Exception {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getPunkBeerList(10))
                .thenReturn(Single.just(punkBeers));
        when(mockDataManager.getLocalPunkBeerList()).thenReturn(Observable.never());

        mainPresenter.getPunkBeers(10);
        verify(mockMainMVPView, times(1))
                .showLoadingProgress(false);
    }


    @Test
    public void whenSubmittedASearchQueryShouldShowFetchedPunkBeers() throws Exception {
        List<PunkBeer> punkBeers = TestData.generatePunkBeerList(10);
        when(mockDataManager.getLocalPunkBeersMatchingName(refEq("foo")))
                .thenReturn(Observable.just(punkBeers));

        mainPresenter.submitSearchQuery("foo");
        verify(mockMainMVPView, times(1))
                .showPunkBeers(punkBeers);
    }
}