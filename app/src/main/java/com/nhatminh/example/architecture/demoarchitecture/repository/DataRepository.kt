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

    /*private var _searchRepos : MutableLiveData<MutableList<GithubRepos>> = MutableLiveData(ArrayList())
    val searchRepos : LiveData<List<GithubRepos>>
        get() = _searchRepos as LiveData<List<GithubRepos>>

    private var _error : MutableLiveData<ERROR> = MutableLiveData(ERROR.NONE)
    val error : LiveData<ERROR>
        get() = _error*/

    private var mReposData: MutableLiveData<ReposData> = MutableLiveData(ReposData())

    fun searchRepos(query: String): LiveData<ReposData> {
        if (!validateQuery(query)) {
            mReposData.value?.state = STATE.ERROR_INVALID_QUERY
        } else {

            mReposData.value?.state = STATE.LOADING

            githubApi.searchRepos(query).enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    handleResponse(response)
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    handleFailure(t)
                }

            })
        }

        return mReposData
    }

    private fun validateQuery(query: String): Boolean {
        if (TextUtils.isEmpty(query)) {
            return false
        }

        return true
    }

    private fun handleResponse(response: Response<SearchResponse>) {
        if (response.isSuccessful) {
            val searchResponse = response.body()

            if (searchResponse != null) {
                mReposData.value?.reposList = searchResponse.searchResults
                mReposData.value?.state = STATE.SUCCESS
            } else {
                mReposData.value?.state = STATE.ERROR_FAILED_RESPONSE
            }
        } else {
            mReposData.value?.state = STATE.ERROR_UNKNOWN
        }
    }

    private fun handleFailure(t: Throwable) {
        mReposData.value?.state = STATE.ERROR_NETWORK
    }

}