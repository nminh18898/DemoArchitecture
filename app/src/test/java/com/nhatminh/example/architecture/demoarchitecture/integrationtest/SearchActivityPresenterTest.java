package com.nhatminh.example.architecture.demoarchitecture.integrationtest;

import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;

import com.nhatminh.example.architecture.demoarchitecture.App;
import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.daggerdi.AppComponent;
import com.nhatminh.example.architecture.demoarchitecture.integrationtest.daggerdi.DaggerTestAppComponent;
import com.nhatminh.example.architecture.demoarchitecture.integrationtest.daggerdi.TestSearchPresenterContractModule;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class SearchActivityPresenterTest {

    SearchActivity activity;

    EditText etSearchQuery;
    Button btSearch;
    RecyclerView rvRepos;
    ProgressBar pbLoading;

    Context context;

    @Mock
    DataRepository repository;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {

        // use test module (fake) instead of using real one
        AppComponent testComponent = DaggerTestAppComponent.builder()
                .testSearchPresenterContractModule(new TestSearchPresenterContractModule(repository))
                .build();

        ((App) ApplicationProvider.getApplicationContext()).setComponent(testComponent);

        /**
         * Notes: see more: https://dagger.dev/testing.html
         */

        // create activity for testing
        activity = Robolectric.buildActivity(SearchActivity.class).create().resume().get();

        // init view
        etSearchQuery = activity.findViewById(R.id.etSearchQuery);
        btSearch = activity.findViewById(R.id.btSearch);
        rvRepos = activity.findViewById(R.id.rvRepos);
        pbLoading = activity.findViewById(R.id.pbLoading);

        // get context to access resource
       context =  InstrumentationRegistry.getInstrumentation().getTargetContext();

    }

    @Test
    public void testActivityShouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void searchSuccessfulWithValidQueryGivenDataRepository(){

        // given (set up)
        String searchQuery = "android";
        List<GithubRepos> fakeReposList = createFakeReposListWithFiveElements();
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((DataRepository.GithubDataRepositoryCallback) invocation.getArguments()[1]).onSuccess(fakeReposList);
                return null;
            }
        }).when(repository).searchRepos(eq(searchQuery),
                any(DataRepository.GithubDataRepositoryCallback.class));

        // when (invoke)
        etSearchQuery.setText(searchQuery);
        btSearch.callOnClick();

        // then (verify)
        verify(repository, times(1))
                .searchRepos(eq(searchQuery), any(DataRepository.GithubDataRepositoryCallback.class));
        assertEquals("RecyclerView not have correct content", 5,  rvRepos.getAdapter().getItemCount());

    }

    @Test
    public void searchFailedWithEmptyQuery_shouldShowEditTextError(){
        // given (set up)
        String searchQuery = "";

        // when (invoke)
        etSearchQuery.setText(searchQuery);
        btSearch.callOnClick();

        // then (verify)
        assertEquals("RecyclerView not have correct content", 0,  rvRepos.getAdapter().getItemCount());
        assertEquals("Edit text not show correct error message",
                context.getString(R.string.this_field_is_required),
                etSearchQuery.getError().toString());
    }

    @Test
    public void searchFailedWithNetworkError_shouldShowToastError(){
        // given (set up)
        String searchQuery = "android";

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                DataRepository.Error error = new DataRepository.Error(DataRepository.ERROR_CODE.NETWORK_ERROR, "Fail to get response");
                ((DataRepository.GithubDataRepositoryCallback) invocation.getArguments()[1]).onError(error);

                return null;
            }
        }).when(repository).searchRepos(eq(searchQuery),
                any(DataRepository.GithubDataRepositoryCallback.class));

        // when (invoke)
        etSearchQuery.setText(searchQuery);
        btSearch.callOnClick();

        // then (verify)
        assert (ShadowToast.showedToast(context.getString(R.string.network_error)));
    }



    @After
    public void tearDown() throws Exception {
        context = null;
    }

    private List<GithubRepos> createFakeReposListWithFiveElements(){
        List<GithubRepos> reposList = new ArrayList<>();

        for(int i=0;i<5;i++) {
            reposList.add(new GithubRepos());
        }
        return reposList;
    }
}
