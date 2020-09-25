package com.nhatminh.example.architecture.demoarchitecture.model

import androidx.lifecycle.MutableLiveData

data class ReposData (var reposList : MutableList<GithubRepos>  = ArrayList(),
                      var state: STATE = STATE.DEFAULT)

enum class STATE{
    DEFAULT,
    SUCCESS,
    LOADING,

    ERROR_NETWORK,
    ERROR_FAILED_RESPONSE,
    ERROR_INVALID_QUERY,
    ERROR_UNKNOWN
}
