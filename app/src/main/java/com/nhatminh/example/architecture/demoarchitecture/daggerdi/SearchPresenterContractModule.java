package com.nhatminh.example.architecture.demoarchitecture.daggerdi;

import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;
import com.nhatminh.example.architecture.demoarchitecture.search.usecases.StoreLastUserQueryUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchPresenterContractModule {

    @Provides
    SearchPresenterContract provideSearchPresenterContract(DataRepository dataRepository, StoreLastUserQueryUseCase storeLastUserQueryUseCase){
        return new SearchPresenter(dataRepository, storeLastUserQueryUseCase);
    }

}
