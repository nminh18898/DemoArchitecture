package com.nhatminh.example.architecture.demoarchitecture.search.presenter;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.idlingresource.EspressoIdlingResource;
import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchViewContract;

import java.util.List;

import javax.inject.Inject;

public class SearchPresenter implements SearchPresenterContract{

    private SearchViewContract viewContract;
    private DataRepository repository;

    List<GithubRepos> repos;

    @Inject
    public SearchPresenter(DataRepository repository) {
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
            viewContract.displayInputEmpty();
            return;
        }

        viewContract.showLoading();

        // idling resource, needed for testing (so ugly)
        EspressoIdlingResource.increment();

        repository.searchRepos(query, new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                repos = reposList;

                viewContract.displaySearchedGithubRepos(repos);

                finishResponse();
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

                    case RESPONSE_NOT_RECEIVED:
                        viewContract.displaySearchError(error.getDescription());
                        break;
                }

                finishResponse();
            }
        });
    }

    @Override
    public void attachView(SearchViewContract view) {
        this.viewContract = view;
    }

    @Override
    public void detachView() {
        if(this.viewContract != null) {
            this.viewContract = null;
        }
    }


    public void finishResponse(){
        viewContract.hideLoading();

        // idling resource, needed for testing (so ugly)
        EspressoIdlingResource.decrement();
    }



}
