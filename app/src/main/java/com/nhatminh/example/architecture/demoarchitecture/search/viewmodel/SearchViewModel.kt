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
        query ->
        val dataFromRepos = repository.searchRepos(query)
        Transformations.switchMap(dataFromRepos){
            reposData ->
            val list = reposData.reposList.filter { it.stargazersCount > 1000 }.sortedByDescending { it.stargazersCount }.toMutableList()
            MutableLiveData<ReposData>(ReposData(list, reposData.state))
        }
    }

    fun searchGithubRepos(query : String){
        queryStr.value = query
    }
}