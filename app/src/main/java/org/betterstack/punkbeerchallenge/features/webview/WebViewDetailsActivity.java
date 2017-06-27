package org.betterstack.punkbeerchallenge.features.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import org.betterstack.punkbeerchallenge.R;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.di.component.ActivityComponent;
import org.betterstack.punkbeerchallenge.features.ErrorView;
import org.betterstack.punkbeerchallenge.features.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class WebViewDetailsActivity extends BaseActivity implements WebViewDetailsMVPView, ErrorView.ErrorListener {

    public static final String EXTRA_BEER_ID = "EXTRA_BEER_ID";


    @Inject
    WebViewDetailsPresenter webViewDetailsPresenter;

    @BindView(R.id.punk_beer_web_view)
    WebView punkBeerImageWebView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_error)
    ErrorView errorView;

    private Long punkBeerId;

    public static Intent getStartIntent(Context context, Long punkBeerId) {
        Intent intent = new Intent(context, WebViewDetailsActivity.class);
        intent.putExtra(EXTRA_BEER_ID, punkBeerId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        punkBeerId = getIntent().getLongExtra(EXTRA_BEER_ID, -1);
        if (punkBeerId == -1) {
            throw new IllegalArgumentException("Web view activity is missing " + EXTRA_BEER_ID);
        }

        errorView.setErrorListener(this);

        webViewDetailsPresenter.getPunkBeerById(punkBeerId);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_detail;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        webViewDetailsPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        webViewDetailsPresenter.detachView();
    }

    @Override
    public void showSelectedPunkBeer(PunkBeer punkBeer) {
        toolbar.setTitle(punkBeer.getName());
        toolbar.setSubtitle(punkBeer.getTagline());
        punkBeerImageWebView.loadUrl(punkBeer.getImageUrl());
    }

    @Override
    public void showError(Throwable error) {
        punkBeerImageWebView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReloadData() {
        webViewDetailsPresenter.getPunkBeerById(punkBeerId);
    }
}
