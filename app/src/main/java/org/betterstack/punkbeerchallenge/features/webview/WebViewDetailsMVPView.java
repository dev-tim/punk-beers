package org.betterstack.punkbeerchallenge.features.webview;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.features.MVPView;

public interface WebViewDetailsMVPView extends MVPView {

    void showSelectedPunkBeer(PunkBeer punkBeer);

    void showError(Throwable error);

}
