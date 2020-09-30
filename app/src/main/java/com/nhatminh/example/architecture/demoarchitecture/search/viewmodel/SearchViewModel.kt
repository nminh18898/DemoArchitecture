package com.nhatminh.example.architecture.demoarchitecture.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository

class SearchViewModel (private val repository: DataRepository) : ViewModel() {

    /*private var _searchRepos : MutableLiveData<MutableList<GithubRepos>> = MutableLiveData(ArrayList())
    val searchRepos : LiveData<List<GithubRepos>>
        get() = _searchRepos as LiveData<List<GithubRepos>>

    private var _error : MutableLiveData<ERROR> = MutableLiveData(ERROR.NONE)
    val error : LiveData<ERROR>
        get() = _error*/

    /*private var _reposData : MutableLiveData<ReposData> = MutableLiveData(ReposData())
    val reposData : LiveData<ReposData>
        get() = _reposData*/

    var reposData : LiveData<ReposData> = MutableLiveData((ReposData()))

    init {
        reposData = repository.reposData
    }

    fun searchGithubRepos(query : String){
        repository.searchRepos(query)
    }
}