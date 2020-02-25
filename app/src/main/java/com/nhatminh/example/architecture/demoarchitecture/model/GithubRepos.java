package com.nhatminh.example.architecture.demoarchitecture.model;

import com.google.gson.annotations.SerializedName;

public class GithubRepos {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("private")
    private Boolean _private;

    @SerializedName("description")
    private String description;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("size")
    private Integer size;

    @SerializedName("stargazers_count")
    private Integer stargazersCount;

    @SerializedName("language")
    private String language;

    @SerializedName("has_wiki")
    private Boolean hasWiki;

    @SerializedName("archived")
    private Boolean archived;

    @SerializedName("score")
    private Double score;


    public GithubRepos() {
        id = 0;
        name = "";
        fullName="";
        _private = true;
        description="";
        updatedAt = "";
        size=0;
        stargazersCount=0;
        language="";
        hasWiki=true;
        archived=true;
        score=0d;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean get_private() {
        return _private;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public Boolean getArchived() {
        return archived;
    }

    public Double getScore() {
        return score;
    }
}
