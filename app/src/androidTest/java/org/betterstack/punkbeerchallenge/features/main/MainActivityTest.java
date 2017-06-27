package org.betterstack.punkbeerchallenge.features.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.betterstack.punkbeerchallenge.R;
import org.betterstack.punkbeerchallenge.TestComponentRule;
import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.betterstack.punkbeerchallenge.features.webview.WebViewDetailsActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final TestComponentRule componentRule =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    private final ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule
    public TestRule chain = RuleChain.outerRule(componentRule).around(mainActivityActivityTestRule);

    @Test
    public void whenFetchedPunkListShouldDisplayItemList() {
        List<PunkBeer> punkBeerList = TestData.generatePunkBeerList(3);

        when(componentRule.getMockDataManager().getPunkBeerList(anyInt()))
                .thenReturn(Single.just(punkBeerList));

        when(componentRule.getMockDataManager().getLocalPunkBeerList())
                .thenReturn(Observable.error(new Throwable("Should not run here!")));

        mainActivityActivityTestRule.launchActivity(null);

        for (PunkBeer punkBeer : punkBeerList) {
            onView(withText(punkBeer.getName())).check(matches(isDisplayed()));
        }
    }

    @Test
    public void whenPunkBeerItemClickedShouldOpenWebViewActivity() {
        List<PunkBeer> punkBeerList = TestData.generatePunkBeerList(10);
        PunkBeer selectedPunkBeer = punkBeerList.get(0);

        Intents.init();

        when(componentRule.getMockDataManager().getPunkBeerList(anyInt()))
                .thenReturn(Single.just(punkBeerList));

        when(componentRule.getMockDataManager().getLocalPunkBeerList())
                .thenReturn(Observable.error(new Throwable("Should not run here!")));

        when(componentRule.getMockDataManager().getLocalPunkBeerById(anyLong()))
                .thenReturn(Single.just(selectedPunkBeer));

        mainActivityActivityTestRule.launchActivity(null);
        onView(withText(selectedPunkBeer.getName())).perform(click());

        intended(
                Matchers.allOf(
                        hasComponent(WebViewDetailsActivity.class.getName()),
                        hasExtra(WebViewDetailsActivity.EXTRA_BEER_ID, selectedPunkBeer.getId())
                )
        );

        Intents.release();
    }

    @Test
    public void whenFailedToFetchPunkBeersShouldShowErrorView() {
        when(componentRule.getMockDataManager().getPunkBeerList(anyInt()))
                .thenReturn(Single.error(new Throwable("Error to get data!")));

        when(componentRule.getMockDataManager().getLocalPunkBeerList())
                .thenReturn(Observable.error(new Throwable("Should not run here!")));

        mainActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.text_error_title)).check(matches(isDisplayed()));
    }

}