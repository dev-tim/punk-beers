package org.betterstack.punkbeerchallenge.features.main;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.features.MVPView;

import java.util.List;

public interface MainMVPView extends MVPView {

    void showPunkBeers(List<PunkBeer> punkBeerNames);

    void showLoadingProgress(boolean isShown);

    void showError(Throwable error);

}
