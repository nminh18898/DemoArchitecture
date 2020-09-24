package com.nhatminh.example.architecture.demoarchitecture.repository

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos

class DataRepository (val githubApi: GithubApi) {

    val searRepos : MutableList<GithubRepos>
}


/* private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = finalScore
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }*/