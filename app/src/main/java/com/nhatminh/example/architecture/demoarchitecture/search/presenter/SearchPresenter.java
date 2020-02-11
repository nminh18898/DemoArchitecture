package com.nhatminh.example.architecture.demoarchitecture.search.presenter;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import java.util.List;

public class SearchPresenter implements SearchPresenterContract {

    private SearchViewContract viewContract;
    private DataRepository repository;

    public SearchPresenter(SearchViewContract viewContract, DataRepository repository) {
        this.viewContract = viewContract;
        this.repository = repository;
    }

    private boolean isQueryEmpty(String query){
        if (query == null || query.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public void searchGithubRepos(String query) {
        if(isQueryEmpty(query)){
            return;
        }

        viewContract.showLoading();

        repository.searchRepos(query, new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                viewContract.displaySearchedGithubRepos(reposList);
                viewContract.hideLoading();
            }

            @Override
            public void onError(DataRepository.Error error) {
                switch (error.getCode()){
                    case INVALID_QUERY:
                        viewContract.displayInputError();
                        break;

                    case NETWORK_ERROR:
                        viewContract.displayConnectionError();
                        break;

                    case RESPONSE_FAILED:
                        viewContract.displaySearchError(error.getDescription());
                        break;
                }
                viewContract.hideLoading();
            }
        });
    }

}
