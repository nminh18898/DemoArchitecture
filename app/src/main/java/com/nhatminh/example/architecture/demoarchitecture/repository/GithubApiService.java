package com.nhatminh.example.architecture.demoarchitecture.repository;

import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubApiService {
    @GET("search/repositories")
    Call<SearchResponse> searchRepos(@Query("q") String term);
}
