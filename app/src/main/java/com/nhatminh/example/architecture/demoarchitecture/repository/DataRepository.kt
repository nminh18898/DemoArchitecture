package com.nhatminh.example.architecture.demoarchitecture.repository

import androidx.lifecycle.MutableLiveData
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.model.STATE

class DataRepository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {

    fun searchRepos(query: String) : MutableLiveData<ReposData> {

        val cached = getDataFromCached(query)

        if(cached != null){
            return cached
        }

        val data = remoteDataSource.searchRepos(query)

        localDataSource.putMemCache(query, data)

        return data
    }

    private fun getDataFromCached(query: String) : MutableLiveData<ReposData>?{
        val cachedData = localDataSource.getMemCache(query)

        if(cachedData != null){
            if(cachedData.value?.state != STATE.SUCCESS){
                localDataSource.removeMemCache(query)
                return null
            }

            return cachedData
        }

        return null
    }


}