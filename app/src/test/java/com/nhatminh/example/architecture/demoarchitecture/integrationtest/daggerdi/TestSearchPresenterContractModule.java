package com.nhatminh.example.architecture.demoarchitecture.integrationtest.daggerdi;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;
import com.nhatminh.example.architecture.demoarchitecture.search.usecases.StoreLastUserQueryUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class TestSearchPresenterContractModule {

    DataRepository dataRepository;
    StoreLastUserQueryUseCase storeLastUserQueryUseCase;

    public TestSearchPresenterContractModule(DataRepository dataRepository, StoreLastUserQueryUseCase storeLastUserQueryUseCase) {
        this.dataRepository = dataRepository;
        this.storeLastUserQueryUseCase = storeLastUserQueryUseCase;
    }

    @Provides
    SearchPresenterContract provideSearchPresenterContract(){
        return new SearchPresenter(dataRepository, storeLastUserQueryUseCase);
    }
}
