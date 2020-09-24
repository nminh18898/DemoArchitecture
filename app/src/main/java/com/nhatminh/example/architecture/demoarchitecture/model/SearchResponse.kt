package com.nhatminh.example.architecture.demoarchitecture.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (

        @SerializedName("incomplete_results")
        var incompleteResults : Boolean = false,

        @SerializedName("items")
        var searchResults : MutableList<GithubRepos> = ArrayList()
)