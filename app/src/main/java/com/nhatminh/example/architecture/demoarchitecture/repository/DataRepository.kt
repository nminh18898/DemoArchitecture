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

class DataRepository(val githubApi: GithubApi) {

    private var _reposData: MutableLiveData<ReposData> = MutableLiveData(ReposData())
    val reposData : LiveData<ReposData>
        get() = _reposData

    fun searchRepos(query: String) {
        if (!validateQuery(query)) {
           handleInvalidQuery()
        } else {

            handleLoadingState()

            githubApi.searchRepos(query).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    handleResponse(response)
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    handleFailure(t)
                }

            })
        }
    }

    private fun validateQuery(query: String): Boolean {
        if (TextUtils.isEmpty(query)) {
            return false
        }

        return true
    }

    private fun handleInvalidQuery(){
        _reposData.value = ReposData(ArrayList(), STATE.ERROR_INVALID_QUERY)
    }

    private fun handleLoadingState(){
        _reposData.value = ReposData(ArrayList(), STATE.LOADING)
    }

    private fun handleResponse(response: Response<SearchResponse>) {
        if (response.isSuccessful) {
            val searchResponse = response.body()

            if (searchResponse != null) {
                _reposData.value = ReposData(searchResponse.searchResults, STATE.SUCCESS)
            } else {
                _reposData.value = ReposData(ArrayList(), STATE.ERROR_FAILED_RESPONSE)
            }
        } else {
            _reposData.value = ReposData(ArrayList(), STATE.ERROR_UNKNOWN)
        }
    }

    private fun handleFailure(t: Throwable) {
        _reposData.value = ReposData(ArrayList(), STATE.ERROR_NETWORK)
    }

}