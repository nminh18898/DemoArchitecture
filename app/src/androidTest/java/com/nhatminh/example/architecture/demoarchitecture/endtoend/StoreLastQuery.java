package com.nhatminh.example.architecture.demoarchitecture.endtoend;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.search.idlingresource.EspressoIdlingResource;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StoreLastQuery {

    SearchActivity activity;

    @Rule
    public ActivityTestRule<SearchActivity> activityTestRule =
            new ActivityTestRule<>(SearchActivity.class);

    @Rule
    public ActivityScenarioRule<SearchActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SearchActivity.class);


    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void cacheUserLastSearchQuery_shouldShowLastQueryInEditText_whenActivityRecreated(){
        String searchQuery="android";

        // perform search
        onView(withId(R.id.etSearchQuery)).perform(clearText());
        onView(withId(R.id.etSearchQuery)).perform(typeText(searchQuery));
        onView(withId(R.id.btSearch)).perform(click());

        // recreate activity
        ActivityScenario scenario = activityScenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.recreate();

        // verify if last query has been cached successful
        onView(withId(R.id.etSearchQuery))
                .check(matches(withText(searchQuery)));

    }
}
