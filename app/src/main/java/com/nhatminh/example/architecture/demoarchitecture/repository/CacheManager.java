package com.nhatminh.example.architecture.demoarchitecture.repository;

import android.util.LruCache;

import androidx.annotation.Nullable;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;

import java.util.List;

public class CacheManager {

    // cache 10 queries
    private static final int MAX_SIZE = 10;

    LruCache<String, List<GithubRepos>> githubReposCache;


    public CacheManager() {
        this.githubReposCache = new LruCache<>(MAX_SIZE);
    }

    public void addGithubReposToCache(String query, List<GithubRepos> reposList){
        githubReposCache.put(query, reposList);
    }

    public @Nullable List<GithubRepos> findGithubReposInCache(String query){
        return githubReposCache.get(query);
    }


}
