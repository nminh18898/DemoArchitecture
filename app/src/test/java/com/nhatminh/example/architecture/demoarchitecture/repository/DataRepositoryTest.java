package com.nhatminh.example.architecture.demoarchitecture.repository;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

    private MockWebServer mockServer;

    private GithubApiService githubApiService;

    private DataRepository repository;

    @Before
    public void setUp() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();

        RetrofitClient.setBaseUrl(mockServer.url("/").toString());
        githubApiService = RetrofitClient.getClient().create(GithubApiService.class);

        repository = new DataRepository(githubApiService);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.shutdown();
    }

    @Test
    public void searchReposWithValidQueryAndWorkingServer() throws InterruptedException {
        // set up
        String query = "android";
        List<GithubRepos> resultList = new ArrayList<>();

        CountDownLatch latch = new CountDownLatch(1);

        DataRepository.GithubDataRepositoryCallback callback = new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                resultList.addAll(reposList);
                latch.countDown();
            }

            @Override
            public void onError(DataRepository.Error error) {
                latch.countDown();
            }
        };

        mockServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(MockGithubServerResponse.SERVER_SUCCESS_SEARCH_RESPONSE));

        // invoke
        repository.searchRepos(query, callback);

        latch.await();

        // verify
        assertEquals("Result list not match with expected", 1, resultList.size());
    }

    @Test
    public void searchReposWithInvalidQuery_shouldFireInvalidQueryError(){
        String query = "&";
        CountDownLatch latch = new CountDownLatch(1);

        final DataRepository.ERROR_CODE[] errorCode = new DataRepository.ERROR_CODE[1];

        DataRepository.GithubDataRepositoryCallback callback = new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                latch.countDown();
            }

            @Override
            public void onError(DataRepository.Error error) {
                errorCode[0] = error.getCode();
                latch.countDown();
            }
        };

        // invoke
        repository.searchRepos(query, callback);

        // verify
        assertEquals("Not catch invalid query error",
                DataRepository.ERROR_CODE.INVALID_QUERY, errorCode[0]);
    }

    @Test
    public void searchReposWithValidQueryAndNotWorkingServer_shouldFireUnknownError() throws InterruptedException {
        String query = "android";
        CountDownLatch latch = new CountDownLatch(1);
        final DataRepository.ERROR_CODE[] errorCode = new DataRepository.ERROR_CODE[1];

        DataRepository.GithubDataRepositoryCallback callback = new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                latch.countDown();
            }

            @Override
            public void onError(DataRepository.Error error) {
                errorCode[0] = error.getCode();
                latch.countDown();
            }
        };

        mockServer.enqueue(new MockResponse()
                .setResponseCode(500));

        // invoke
        repository.searchRepos(query, callback);

        latch.await();

        // verify
        assertEquals("Not catch invalid query error",
                DataRepository.ERROR_CODE.UNKNOWN, errorCode[0]);
    }
}
