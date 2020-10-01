package com.nhatminh.example.architecture.demoarchitecture.repository

import androidx.lifecycle.MutableLiveData
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData

class LocalDataSource {

    private var reposMemCached : HashMap<String, MutableLiveData<ReposData>> = HashMap()

    fun putMemCache(keyword : String, repos : MutableLiveData<ReposData>){
        reposMemCached[keyword] = repos
    }

    fun getMemCache(keyword : String) : MutableLiveData<ReposData>?{
        return reposMemCached[keyword]
    }

    fun removeMemCache(keyword : String){
        reposMemCached.remove(keyword)
    }
}