package com.nhatminh.example.architecture.demoarchitecture.view;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.search.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class SearchViewTest {

    MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initView(){

       activity = activityRule.getActivity();
    }


    @Test
    public void testShowLoading() throws Throwable {
        activityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showLoading();
            }
        });

        onView(ViewMatchers.withId(R.id.pbLoading)).check(matches(isDisplayed()));
    }

    @Test
    public void testProgressBarInitialState(){
        onView(withId(R.id.pbLoading)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testSearchButtonInitialState(){
        onView(withId(R.id.btSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.btSearch)).check(matches(isClickable()));
        onView(withId(R.id.btSearch)).check(matches(withText(R.string.search)));
    }

    @Test
    public void testEditTextSearchQueryInitialState(){
        onView(withId(R.id.etSearchQuery)).check(matches(isDisplayed()));
        onView(withId(R.id.etSearchQuery)).check(matches(withText("")));
        onView(withId(R.id.etSearchQuery)).check(matches(isEnabled()));
    }

    @Test
    public void testRecyclerViewInitialState(){
        onView(withId(R.id.rvRepos)).check(matches(isDisplayed()));
        onView(withId(R.id.rvRepos)).check(matches(hasChildCount(0)));
    }





   /*@UiThreadTest
    @Test
    public void testShowLoading() {
        //viewContract.showLoading();
        onView(withId(R.id.pbLoading)).check(matches(isDisplayed()));
        onView(withId(R.id.btSearch)).check(matches(isDisplayed()));
    }*/

}
