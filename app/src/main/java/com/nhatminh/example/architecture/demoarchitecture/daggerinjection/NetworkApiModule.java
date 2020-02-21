package com.nhatminh.example.architecture.demoarchitecture.daggerinjection;

import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApiService;
import com.nhatminh.example.architecture.demoarchitecture.repository.RetrofitClient;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkApiModule {

    @Provides
    GithubApiService provideGithubApiService(){
        return RetrofitClient.getClient().create(GithubApiService.class);
    }

}
