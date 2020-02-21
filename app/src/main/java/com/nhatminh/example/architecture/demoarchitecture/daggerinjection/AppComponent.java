package com.nhatminh.example.architecture.demoarchitecture.daggerinjection;

import com.nhatminh.example.architecture.demoarchitecture.search.view.MainActivity;

import dagger.Component;

@Component(modules = NetworkApiModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
