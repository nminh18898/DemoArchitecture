package com.nhatminh.example.architecture.demoarchitecture.search.view;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;

import java.util.List;

public interface SearchViewContract {

    void showLoading();
    void hideLoading();

    void displayInputEmpty();
    void displayInputError();

    void displaySearchedGithubRepos(List<GithubRepos> reposList);
    void displayConnectionError();
    void displaySearchError(String errorMessage);

}
