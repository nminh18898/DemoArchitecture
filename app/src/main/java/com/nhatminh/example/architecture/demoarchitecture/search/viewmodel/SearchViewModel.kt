package com.nhatminh.example.architecture.demoarchitecture.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository

class SearchViewModel (private val repository: DataRepository) : ViewModel() {

    var reposData : LiveData<ReposData> = MutableLiveData((ReposData()))

    init {
        reposData = repository.reposData
    }

    fun searchGithubRepos(query : String){
        repository.searchRepos(query)
    }
}