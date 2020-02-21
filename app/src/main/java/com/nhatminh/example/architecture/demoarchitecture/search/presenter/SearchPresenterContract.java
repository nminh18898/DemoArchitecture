package com.nhatminh.example.architecture.demoarchitecture.search.presenter;

import androidx.lifecycle.LifecycleObserver;

import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

public interface SearchPresenterContract extends LifecycleObserver {
    void searchGithubRepos(String query);

    void attachView(SearchViewContract view);
    void detachView();

}
