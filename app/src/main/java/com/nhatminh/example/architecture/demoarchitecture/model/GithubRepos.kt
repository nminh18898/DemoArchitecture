package com.nhatminh.example.architecture.demoarchitecture.model

import com.google.gson.annotations.SerializedName

data class GithubRepos (

        @SerializedName("id")
        var id : Int = -1,

        @SerializedName("full_name")
        var fullName : String = "",

        @SerializedName("private")
        var private : Boolean = false,

        @SerializedName("description")
        var description : String = "",

        @SerializedName("updated_at")
        var updatedAt : String = "",

        @SerializedName("size")
        var size : Int = -1,

        @SerializedName("stargazers_count")
        var stargazersCount : Int = -1,

        @SerializedName("language")
        var language : String = "",

        @SerializedName("has_wiki")
        var hasWiki : Boolean = false,

        @SerializedName("archived")
        var archived : Boolean = false,

        @SerializedName("score")
        var score : Double = 0.0
)