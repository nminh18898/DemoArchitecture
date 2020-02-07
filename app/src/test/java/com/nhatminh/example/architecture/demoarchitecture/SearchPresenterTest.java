package com.nhatminh.example.architecture.demoarchitecture;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApi;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchPresenterTest {

    private SearchPresenter presenter;

    @Mock
    private DataRepository repository;

    @Mock
    private SearchViewContract viewContract;

    @Mock
    private GithubApi githubApi;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        repository = new DataRepository(githubApi);

        presenter = new SearchPresenter(viewContract, repository);
    }

    @Test
    public void searchGitHubRepos_noQuery() {
        String searchQuery = null;

        presenter.searchGithubRepos(searchQuery);

        Mockito.verify(repository, Mockito.never()).searchRepos(searchQuery, null);

        Mockito.verify(viewContract, Mockito.times(1)).showLoading();

        //Mockito.verify(viewContract, Mockito.times(1)).hideLoading();

        //Mockito.verify(viewContract, Mockito.times(1)).displayInputError();

        Mockito.verify(viewContract, Mockito.never()).displaySearchedGithubRepos(null);
    }

    @Test
    public void searchGitHubRepos() {


    }


}
