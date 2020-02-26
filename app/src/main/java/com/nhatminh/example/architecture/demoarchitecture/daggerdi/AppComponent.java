package com.nhatminh.example.architecture.demoarchitecture.daggerdi;

import com.nhatminh.example.architecture.demoarchitecture.search.view.SearchActivity;

import dagger.Component;

@Component(modules = {NetworkApiModule.class, SearchPresenterContractModule.class, AppModule.class})
public interface AppComponent {

    void inject(SearchActivity searchActivity);
}
