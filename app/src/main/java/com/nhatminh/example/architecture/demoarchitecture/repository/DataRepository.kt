package com.nhatminh.example.architecture.demoarchitecture.repository

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.model.STATE
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(private val remoteDataSource: RemoteDataSource) {

    fun searchRepos(query: String) : MutableLiveData<ReposData> {
        return remoteDataSource.searchRepos(query)
    }

}