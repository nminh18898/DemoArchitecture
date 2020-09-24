package com.nhatminh.example.architecture.demoarchitecture.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val GITHUB_BASE_URL = "https://api.github.com"

    val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}