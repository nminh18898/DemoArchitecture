package com.nhatminh.example.architecture.demoarchitecture.endtoend;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.assertion.RecyclerViewItemCountAssertion;
import com.nhatminh.example.architecture.demoarchitecture.search.idlingresource.EspressoIdlingResource;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class SearchFlow {

    SearchActivity activity;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule =
            new ActivityTestRule<>(SearchActivity.class);

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
    public void searchSuccessfulWithValidQuery_shouldShowRecyclerViewWith30Items() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.etSearchQuery)).perform(typeText("android"));
        onView(withId(R.id.btSearch)).perform(click());

        // onView(withId(R.id.pbLoading)).check(matches(isDisplayed())); // not found how to test yet

        onView(withId(R.id.rvRepos)).check(new RecyclerViewItemCountAssertion(30));
        onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())));
    }

    @Test
    public void searchFailedWithEmptyQuery_shouldShowEditTextError(){
        onView(ViewMatchers.withId(R.id.etSearchQuery)).perform(typeText(""));
        onView(withId(R.id.btSearch)).perform(click());

        onView(withId(R.id.etSearchQuery))
                .check(matches(hasErrorText(InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.this_field_is_required))));
        onView(withId(R.id.rvRepos)).check(matches(isDisplayed()));
        onView(withId(R.id.rvRepos)).check(matches(hasChildCount(0)));
    }



}
