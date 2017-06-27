package org.betterstack.punkbeerchallenge.features.webview;

import org.betterstack.punkbeerchallenge.RxSchedulersOverrideRule;
import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.DataManager;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.features.main.MainPresenter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WebViewDetailsPresenterTest {

    @Mock
    private DataManager mockDataManager;

    @Mock
    private WebViewDetailsMVPView mockWebViewDetailsMVPView;


    private WebViewDetailsPresenter webViewDetailsPresenter;

    @Rule
    public final RxSchedulersOverrideRule customSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() throws Exception {
        webViewDetailsPresenter = new WebViewDetailsPresenter(mockDataManager);
        webViewDetailsPresenter.attachView(mockWebViewDetailsMVPView);
    }

    @After
    public void tearDown() throws Exception {
        webViewDetailsPresenter.detachView();
    }

    @Test
    public void whenRequestedPunkBeerByIdShouldShowPunkBeerToView() throws Exception {
        PunkBeer punkBeer = TestData.generatePunkBeer(42);

        when(mockDataManager.getLocalPunkBeerById(refEq(punkBeer.getId())))
                .thenReturn(Single.just(punkBeer));

        webViewDetailsPresenter.getPunkBeerById(42L);

        verify(mockWebViewDetailsMVPView, times(1))
                .showSelectedPunkBeer(punkBeer);
    }

    @Test
    public void whenFailedToFetchPunkBeerByIdShouldShowError() throws Exception {
        PunkBeer punkBeer = TestData.generatePunkBeer(42);

        when(mockDataManager.getLocalPunkBeerById(refEq(punkBeer.getId())))
                .thenReturn(Single.error(new Throwable("Failed to request beer")));

        webViewDetailsPresenter.getPunkBeerById(42L);

        verify(mockWebViewDetailsMVPView, times(1))
                .showError(any(Throwable.class));
    }

}