package com.nhatminh.example.architecture.demoarchitecture;

import android.app.Application;

import com.nhatminh.example.architecture.demoarchitecture.daggerinjection.AppComponent;
import com.nhatminh.example.architecture.demoarchitecture.daggerinjection.DaggerAppComponent;
import com.nhatminh.example.architecture.demoarchitecture.daggerinjection.NetworkApiModule;
import com.nhatminh.example.architecture.demoarchitecture.daggerinjection.SearchPresenterContractModule;

public class App extends Application {

    private static AppComponent component;

    public static AppComponent getComponent(){
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .networkApiModule(new NetworkApiModule())
                .searchPresenterContractModule(new SearchPresenterContractModule())
                .build();
    }
}
