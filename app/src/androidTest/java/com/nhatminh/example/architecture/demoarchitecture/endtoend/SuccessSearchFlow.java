package com.nhatminh.example.architecture.demoarchitecture.endtoend;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.assertion.RecyclerViewItemCountAssertion;
import com.nhatminh.example.architecture.demoarchitecture.search.idlingresource.EspressoIdlingResource;
import com.nhatminh.example.architecture.demoarchitecture.search.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class SuccessSearchFlow {

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void searchWithValidQueryCompleteFlow() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.etSearchQuery)).perform(typeText("android"));
        onView(withId(R.id.btSearch)).perform(click());

        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()));
        onView(withId(R.id.rvRepos)).check(new RecyclerViewItemCountAssertion(30));
        onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())));
    }

}
