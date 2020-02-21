package com.nhatminh.example.architecture.demoarchitecture;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class SearchPresenterTest {

    private SearchPresenterContract presenter;

    List<GithubRepos> fakeListRepos;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DataRepository repository;

    @Mock
    private SearchViewContract viewContract;

    @Before
    public void setup() throws Exception{
        presenter = new SearchPresenter(repository);
        presenter.attachView(viewContract);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detachView();
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

        // verify
        verify(repository, times(1))
                .searchRepos(eq(searchQuery), any(DataRepository.GithubDataRepositoryCallback.class));

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

    // for experiment
    DataRepository.GithubDataRepositoryCallback errorNoInternetCallback;

    @Test
    public void searchGithubRepos_noInternet() {
        // set up elements
        String searchQuery = "android";
        DataRepository.Error errorNoInternet
                = new DataRepository.Error(DataRepository.ERROR_CODE.NETWORK_ERROR, "No internet access");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                errorNoInternetCallback = ((DataRepository.GithubDataRepositoryCallback) invocation.getArguments()[1]);
                errorNoInternetCallback.onError(errorNoInternet);
                return null;
            }
        }).when(repository).searchRepos(eq(searchQuery),
                any(DataRepository.GithubDataRepositoryCallback.class));


        // invoke
        presenter.searchGithubRepos(searchQuery);

        // verify repository
        verify(repository, times(1))
                .searchRepos(eq(searchQuery), eq(errorNoInternetCallback));

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
