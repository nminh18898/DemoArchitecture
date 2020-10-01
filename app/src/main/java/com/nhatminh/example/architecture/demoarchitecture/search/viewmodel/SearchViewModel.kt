package com.nhatminh.example.architecture.demoarchitecture.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository

class SearchViewModel (private val repository: DataRepository) : ViewModel() {

    private val queryStr = MutableLiveData<String>()

    var reposData : LiveData<ReposData> = Transformations.switchMap(queryStr){
        query -> repository.searchRepos(query)
    }

    fun searchGithubRepos(query : String){
        queryStr.value = query
    }
}