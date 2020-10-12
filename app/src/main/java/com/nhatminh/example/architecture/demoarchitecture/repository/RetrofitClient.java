package com.nhatminh.example.architecture.demoarchitecture.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit retrofit = null;

    private static final String GITHUB_BASE_URL = "https://api.github.com";


    public static Retrofit getClient() {
        if (retrofit==null) {
            synchronized (RetrofitClient.class) {
                if(retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(GITHUB_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

}
