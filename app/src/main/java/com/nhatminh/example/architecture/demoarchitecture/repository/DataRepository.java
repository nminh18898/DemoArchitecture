package com.nhatminh.example.architecture.demoarchitecture.repository;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private GithubApi githubApi;

    public DataRepository(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public void searchRepos(String query, GithubDataRepositoryCallback callback){
        if (!validateQuery(query)){
            callback.onError(new Error(ERROR_CODE.INVALID_QUERY, "Invalid query"));
            return;
        }

        githubApi.searchRepos(query).enqueue(new Callback<SearchResponse>() {
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
        // to-do: check if query is valid or not
        return true;
    }


    private void handleResponse(Response<SearchResponse> response, GithubDataRepositoryCallback callback) {
        if (response.isSuccessful()) {

            SearchResponse searchResponse = response.body();

            if (searchResponse != null) {
                callback.onSuccess(searchResponse.getSearchResults());

            } else {
                callback.onError(new Error(ERROR_CODE.RESPONSE_FAILED, "Response body null"));

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
        RESPONSE_FAILED,
        INVALID_QUERY
    }
}

