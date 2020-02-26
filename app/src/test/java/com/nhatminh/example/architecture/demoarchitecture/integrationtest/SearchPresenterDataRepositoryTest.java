package com.nhatminh.example.architecture.demoarchitecture.integrationtest;

import android.content.Context;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApiService;
import com.nhatminh.example.architecture.demoarchitecture.repository.MockGithubServerResponse;
import com.nhatminh.example.architecture.demoarchitecture.repository.RetrofitClient;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;
import com.nhatminh.example.architecture.demoarchitecture.search.usecases.StoreLastUserQueryUseCase;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

public class SearchPresenterDataRepositoryTest {

    SearchPresenterContract presenter;

    DataRepository repository;

    StoreLastUserQueryUseCase storeLastUserQueryUseCase;

    private GithubApiService githubApiService;

    private MockWebServer mockServer;

    @Mock
    SearchViewContract view;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Context context;

    @Before
    public void setUp() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();

        RetrofitClient.setBaseUrl(mockServer.url("/").toString());
        githubApiService = RetrofitClient.getClient().create(GithubApiService.class);

        repository = new DataRepository(githubApiService);

        storeLastUserQueryUseCase = new StoreLastUserQueryUseCase(context);

        presenter = new SearchPresenter(repository, storeLastUserQueryUseCase);

        presenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.shutdown();
        presenter.detachView();
    }

    @Test
    public void testPresenterSearchGithubRepos() throws InterruptedException {
        // given (set up)
        String searchQuery = "android";

        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(MockGithubServerResponse.SERVER_SUCCESS_SEARCH_RESPONSE));

        // when (invoke)
        presenter.searchGithubRepos(searchQuery);

        /**
         * Another ugly workaround for asynchronous, should not be used (use idling resource instead, less ugly)
         */
        Thread.sleep(3000);

        // then (verify)
        InOrder viewOrder = inOrder(view);
        viewOrder.verify(view, times(1)).showLoading();
        viewOrder.verify(view, times(1)).displaySearchedGithubRepos(any());
        viewOrder.verify(view, times(1)).hideLoading();
    }

}
