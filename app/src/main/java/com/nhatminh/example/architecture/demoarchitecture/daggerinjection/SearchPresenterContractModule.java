package com.nhatminh.example.architecture.demoarchitecture.daggerinjection;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchPresenterContractModule {

    @Provides
    SearchPresenterContract provideSearchPresenterContract(DataRepository dataRepository){
        return new SearchPresenter(dataRepository);
    }

}
