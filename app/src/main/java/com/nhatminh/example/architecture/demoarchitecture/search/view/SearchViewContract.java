package com.nhatminh.example.architecture.demoarchitecture.search.view;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;

import java.util.List;

public interface SearchViewContract {

    void showLoading();
    void hideLoading();

    void displaySearchedGithubRepos(List<GithubRepos> reposList);

    void displayInputEmpty();
    void displayInputError();
    void displayConnectionError();
    void displaySearchError(String errorMessage);

    void showToast(String message);

    void navigateToHomeActivity();

}
