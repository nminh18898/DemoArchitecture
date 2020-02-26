package com.nhatminh.example.architecture.demoarchitecture.repository;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private GithubApiService githubApiService;

    @Inject
    public DataRepository(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    public void searchRepos(String query, GithubDataRepositoryCallback callback){
        if (!validateQuery(query)){
            callback.onError(new Error(ERROR_CODE.INVALID_QUERY, "Invalid query"));
            return;
        }

        // request from server
        githubApiService.searchRepos(query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                handleResponse(response, callback);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callback.onError(new Error(ERROR_CODE.NETWORK_ERROR, "Fail to get response"));

            }
        });
    }

    private boolean validateQuery(String query) {
        // to-do: add more case
        if(query.contains("|") || query.contains("&")){
            return false;
        }

        return true;
    }

    private void handleResponse(Response<SearchResponse> response, GithubDataRepositoryCallback callback) {
        if (response.isSuccessful()) {

            SearchResponse searchResponse = response.body();

            if (searchResponse != null) {
                callback.onSuccess(searchResponse.getSearchResults());

            } else {
                callback.onError(new Error(ERROR_CODE.RESPONSE_NOT_RECEIVED, "Response body null"));

            }

        } else {
            callback.onError(new Error(ERROR_CODE.UNKNOWN, "Unknown error"));

        }
    }

    public interface GithubDataRepositoryCallback{

        void onSuccess(List<GithubRepos> reposList);
        void onError(Error error);

    }

    public static class Error{
        private ERROR_CODE code;
        private String description;

        public Error(ERROR_CODE code, String description) {
            this.code = code;
            this.description = description;
        }

        public ERROR_CODE getCode() {
            return code;
        }

        public void setCode(ERROR_CODE code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public enum ERROR_CODE{
        UNKNOWN,
        NETWORK_ERROR,
        RESPONSE_NOT_RECEIVED,
        INVALID_QUERY
    }
}

