package com.nhatminh.example.architecture.demoarchitecture.integrationtest.daggerdi;

import com.nhatminh.example.architecture.demoarchitecture.daggerdi.AppComponent;

import dagger.Component;

@Component(modules = TestSearchPresenterContractModule.class)
public interface TestAppComponent extends AppComponent {
}
