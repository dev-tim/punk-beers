package org.betterstack.punkbeerchallenge.features.webview;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.betterstack.punkbeerchallenge.R;
import org.betterstack.punkbeerchallenge.TestComponentRule;
import org.betterstack.punkbeerchallenge.TestData;
import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import io.reactivex.Single;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class WebViewDetailsActivityTest {

    private final TestComponentRule componentRule =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    private final ActivityTestRule<WebViewDetailsActivity> webViewDetailsActivityActivityTestRule =
            new ActivityTestRule<>(WebViewDetailsActivity.class, false, false);

    @Rule
    public TestRule chain = RuleChain.outerRule(componentRule).around(webViewDetailsActivityActivityTestRule);


    @Test
    public void whenIntentWithExtraReceivedShouldRenderWebView() throws Exception {
        PunkBeer selectedPunkBeer = TestData.generatePunkBeer(42);
        when(componentRule.getMockDataManager().getLocalPunkBeerById(anyLong()))
                .thenReturn(Single.just(selectedPunkBeer));

        launchWebViewDetailsActivity();

        onView(withId(R.id.punk_beer_web_view)).check(matches(isDisplayed()));
    }

    @Test
    public void whenIntentWithReceivedShouldSetActionBarTitles() throws Exception {
        PunkBeer selectedPunkBeer = TestData.generatePunkBeer(42);
        when(componentRule.getMockDataManager().getLocalPunkBeerById(anyLong()))
                .thenReturn(Single.just(selectedPunkBeer));

        launchWebViewDetailsActivity();

        onView(withText(selectedPunkBeer.getName())).check(matches(isDisplayed()));
        onView(withText(selectedPunkBeer.getTagline())).check(matches(isDisplayed()));
    }

    private void launchWebViewDetailsActivity() {
        webViewDetailsActivityActivityTestRule.launchActivity(prepareIntentWithExtras());
    }

    private Intent prepareIntentWithExtras() {
        Intent intent = new Intent();
        intent.putExtra(WebViewDetailsActivity.EXTRA_BEER_ID, 42L);
        return intent;
    }
}