package com.nhatminh.example.architecture.demoarchitecture;

import android.app.Application;

import com.nhatminh.example.architecture.demoarchitecture.daggerdi.AppComponent;
import com.nhatminh.example.architecture.demoarchitecture.daggerdi.AppModule;
import com.nhatminh.example.architecture.demoarchitecture.daggerdi.DaggerAppComponent;
import com.nhatminh.example.architecture.demoarchitecture.daggerdi.NetworkApiModule;
import com.nhatminh.example.architecture.demoarchitecture.daggerdi.SearchPresenterContractModule;

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
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }


    public void setComponent(AppComponent component) {
        App.component = component;
    }
}
