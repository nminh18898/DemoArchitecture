package com.nhatminh.example.architecture.demoarchitecture.integrationtest.daggerdi;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TestSearchPresenterContractModule {

    DataRepository dataRepository;

    public TestSearchPresenterContractModule(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Provides
    SearchPresenterContract provideSearchPresenterContract(){
        return new SearchPresenter(dataRepository);
    }
}
