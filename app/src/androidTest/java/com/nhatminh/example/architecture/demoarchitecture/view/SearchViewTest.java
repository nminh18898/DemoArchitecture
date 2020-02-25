package com.nhatminh.example.architecture.demoarchitecture.view;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.assertion.RecyclerViewItemCountAssertion;
import com.nhatminh.example.architecture.demoarchitecture.home.HomeActivity;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class SearchViewTest {

    SearchActivity activity;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule =
            new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void initView(){

       activity = activityRule.getActivity();

        Intents.init();
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


    @Test
    public void testDisplaySearchedGithubReposWith10ItemsList_shouldHave10Items() throws Throwable {
        activityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<GithubRepos> reposList = createMockReposList();
                activity.displaySearchedGithubRepos(reposList);
            }
        });

        onView(withId(R.id.rvRepos)).check(new RecyclerViewItemCountAssertion(10));

    }

    @Test
    public void testRecyclerViewItemClicked_shouldShowToast() throws Throwable {

        // given (set up)
        activityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<GithubRepos> reposList = createMockReposList();
                activity.displaySearchedGithubRepos(reposList);
            }
        });

        // when (invoke)
        int positionClicked = 0;

        onView(withId(R.id.rvRepos))
                .perform((RecyclerViewActions.actionOnItemAtPosition(positionClicked, click())));

        // verify
        String toastText = "Click: " + positionClicked;

        onView(withText(toastText))
                .inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testRecyclerViewItemLongClicked_shouldStartHomeActivity() throws Throwable {

        // given (set up)
        activityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<GithubRepos> reposList = createMockReposList();
                activity.displaySearchedGithubRepos(reposList);
            }
        });

        // when (invoke)
        int positionClicked = 0;

        onView(withId(R.id.rvRepos))
                .perform((RecyclerViewActions.actionOnItemAtPosition(positionClicked, longClick())));

        // then (verify)
        intended(hasComponent(HomeActivity.class.getName()));

    }

    private List<GithubRepos> createMockReposList(){
        List<GithubRepos> reposList = new ArrayList<>();

        for(int i=0;i<10;i++) {
            reposList.add(new GithubRepos());
        }
        return reposList;
    }

}
