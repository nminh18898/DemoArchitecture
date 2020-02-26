package com.nhatminh.example.architecture.demoarchitecture.daggerdi;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext(){
        return context;
    }
}
