package com.nhatminh.example.architecture.demoarchitecture.repository;

import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GithubApi {
    @Headers({"Accept: application/vnd.github.mercy-preview+json"})
    @GET("search/repositories")
    Call<SearchResponse> searchRepos(@Query("q") String term);
}
