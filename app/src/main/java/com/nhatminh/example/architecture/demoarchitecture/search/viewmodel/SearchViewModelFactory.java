package com.nhatminh.example.architecture.demoarchitecture.search.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private DataRepository repository;

    public SearchViewModelFactory(DataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchViewModel(repository);
    }
}
