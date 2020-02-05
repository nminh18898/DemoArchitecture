package com.nhatminh.example.architecture.demoarchitecture.search.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;

import java.util.List;

public class SearchViewModel {

    private DataRepository repository;

    private MutableLiveData<List<GithubRepos>> reposListLiveData;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private MutableLiveData<DataRepository.Error> errorMessage = new MutableLiveData<>();

    public SearchViewModel(DataRepository repository) {
        this.repository = repository;
        reposListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<GithubRepos>> getReposListLiveData() {
        return reposListLiveData;
    }

    public MutableLiveData<DataRepository.Error> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void searchGithubRepos(String query) {
        isLoading.setValue(true);

        repository.searchRepos(query, new DataRepository.GithubDataRepositoryCallback() {
            @Override
            public void onSuccess(List<GithubRepos> reposList) {
                reposListLiveData.setValue(reposList);
                isLoading.setValue(false);
            }

            @Override
            public void onError(DataRepository.Error error) {
                errorMessage.setValue(error);
                isLoading.setValue(false);
            }
        });
    }
}
