package com.nhatminh.example.architecture.demoarchitecture;

import androidx.annotation.NonNull;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApi;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchPresenterTest {

    private SearchPresenter presenter;

    List<GithubRepos> fakeListRepos;

    @Mock
    private DataRepository repository;

    @Mock
    private SearchViewContract viewContract;

    @Mock
    private GithubApi githubApi;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        //repository = new DataRepository(githubApi);

        presenter = new SearchPresenter(viewContract, repository);
    }

    @Test
    public void searchGithubRepos_validQuery() {
        // set up elements
        String searchQuery = "android";
        fakeListRepos = createFakeReposList();
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((DataRepository.GithubDataRepositoryCallback) invocation.getArguments()[1]).onSuccess(fakeListRepos);
                return null;
            }
        }).when(repository).searchRepos(eq(searchQuery),
                any(DataRepository.GithubDataRepositoryCallback.class));

        // invoke
        presenter.searchGithubRepos(searchQuery);

        // verify repository behavior
        verify(repository, times(1))
                .searchRepos(eq(searchQuery), any(DataRepository.GithubDataRepositoryCallback.class));

        // verify view behavior
        InOrder viewOrder = inOrder(viewContract);
        viewOrder.verify(viewContract, times(1)).showLoading();
        viewOrder.verify(viewContract, times(1)).displaySearchedGithubRepos(fakeListRepos);
        viewOrder.verify(viewContract, times(1)).hideLoading();
    }

    @Test
    public void searchGithubRepos_emptyQuery(){
        // set up elements
        String searchQuery = "";

        // invoke
        presenter.searchGithubRepos(searchQuery);

        // verify repository behavior
        verify(repository, times(0))
                .searchRepos(eq(searchQuery), any(DataRepository.GithubDataRepositoryCallback.class));

        // verify view behavior
        verify(viewContract, times(0)).showLoading();
        verify(viewContract, times(0)).displaySearchedGithubRepos(fakeListRepos);
        verify(viewContract, times(0)).hideLoading();
    }

    @Test
    public void searchGithubRepos_noInternet() {
        // set up elements
        String searchQuery = "android";
        DataRepository.Error errorNoInternet
                = new DataRepository.Error(DataRepository.ERROR_CODE.NETWORK_ERROR, "No internet access");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((DataRepository.GithubDataRepositoryCallback) invocation.getArguments()[1]).onError(errorNoInternet);
                return null;
            }
        }).when(repository).searchRepos(eq(searchQuery),
                any(DataRepository.GithubDataRepositoryCallback.class));


        // invoke
        presenter.searchGithubRepos(searchQuery);

        // verify view behavior
        InOrder viewOrder = inOrder(viewContract);
        viewOrder.verify(viewContract, times(1)).showLoading();
        viewOrder.verify(viewContract, times(1)).displayConnectionError();
        viewOrder.verify(viewContract, times(1)).hideLoading();
    }

    private List<GithubRepos> createFakeReposList(){
        List<GithubRepos> reposList = new ArrayList<>();

        for(int i=0;i<5;i++) {
            reposList.add(new GithubRepos());
        }
        return reposList;
    }
}
