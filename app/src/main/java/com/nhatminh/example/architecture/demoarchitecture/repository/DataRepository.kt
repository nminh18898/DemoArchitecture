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

    fun searchRepos(query: String) : MutableLiveData<ReposData> {
        val reposData = MutableLiveData(ReposData())

        if (!validateQuery(query)) {
            handleInvalidQuery(reposData)
        } else {

            handleLoadingState(reposData)

            githubApi.searchRepos(query).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    handleResponse(response, reposData)
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    handleFailure(t, reposData)
                }

            })
        }

        return reposData
    }

    private fun validateQuery(query: String): Boolean {
        if (TextUtils.isEmpty(query)) {
            return false
        }

        return true
    }

    private fun handleInvalidQuery(reposData : MutableLiveData<ReposData>){
        reposData.value = ReposData(ArrayList(), STATE.ERROR_INVALID_QUERY)
    }

    private fun handleLoadingState(reposData : MutableLiveData<ReposData>){
        reposData.value = ReposData(ArrayList(), STATE.LOADING)
    }

    private fun handleResponse(response: Response<SearchResponse>, reposData : MutableLiveData<ReposData>) {
        if (response.isSuccessful) {
            val searchResponse = response.body()

            if (searchResponse != null) {
                reposData.value = ReposData(searchResponse.searchResults, STATE.SUCCESS)
            } else {
                reposData.value = ReposData(ArrayList(), STATE.ERROR_FAILED_RESPONSE)
            }
        } else {
            reposData.value = ReposData(ArrayList(), STATE.ERROR_UNKNOWN)
        }
    }

    private fun handleFailure(t: Throwable, reposData : MutableLiveData<ReposData>) {
        reposData.value = ReposData(ArrayList(), STATE.ERROR_NETWORK)
    }

}