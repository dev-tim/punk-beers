package org.betterstack.punkbeerchallenge.features.main;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import org.betterstack.punkbeerchallenge.R;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.di.component.ActivityComponent;
import org.betterstack.punkbeerchallenge.features.ErrorView;
import org.betterstack.punkbeerchallenge.features.base.BaseActivity;
import org.betterstack.punkbeerchallenge.features.webview.WebViewDetailsActivity;
import org.betterstack.punkbeerchallenge.utils.ConnectivityUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMVPView, ErrorView.ErrorListener {

    @Inject
    PunkBeerAdapter punkBeerAdapter;

    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_punk_beer)
    RecyclerView punkBeerRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // TODO move to the config, however there is no beer limit!
    private static final int BEER_LIMIT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(this::loadBunkBeers);

        punkBeerRecycler.setLayoutManager(new LinearLayoutManager(this));
        punkBeerRecycler.setAdapter(punkBeerAdapter);

        errorView.setErrorListener(this);

        handlePunkBeerClicked();
        loadBunkBeers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.menu_search_item);
        if (searchItem != null) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView searchView = (SearchView) searchItem.getActionView();

            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setIconified(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searchView.clearFocus();
                        mainPresenter.submitSearchQuery(s);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        mainPresenter.updateSearch(s);
                        return true;
                    }
                });
            }
        }

        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showPunkBeers(List<PunkBeer> punkBeers) {
        punkBeerAdapter.setPunkBeerList(punkBeers);
        punkBeerRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingProgress(boolean isShown) {
        if (isShown) {
            if (punkBeerRecycler.getVisibility() == View.VISIBLE
                    && punkBeerAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                punkBeerRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        punkBeerRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the punk beer");
    }

    @Override
    public void onReloadData() {
        loadBunkBeers();
    }

    private void loadBunkBeers() {
        if (ConnectivityUtil.isConnected(this)){
            mainPresenter.getPunkBeers(BEER_LIMIT);
        } else {
            mainPresenter.getLocalPunkBeers();
        }
    }

    private void handlePunkBeerClicked() {
        mainPresenter.addDisposable(punkBeerAdapter
                .getPunkBeerClickSubject()
                .subscribe(
                        beer -> {
                            Timber.i("Clicked on punk beer %s", beer.getId());
                            startActivity(WebViewDetailsActivity.getStartIntent(this, beer.getId()));
                        },
                        throwable -> {
                            Timber.e(throwable, "Click handling failed");
                            Toast.makeText(this, R.string.error_something_bad_happened,
                                    Toast.LENGTH_LONG).show();
                        }));
    }
}
