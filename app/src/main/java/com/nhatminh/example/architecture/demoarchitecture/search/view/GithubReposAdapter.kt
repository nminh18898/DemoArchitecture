package com.nhatminh.example.architecture.demoarchitecture.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nhatminh.example.architecture.demoarchitecture.R
import com.nhatminh.example.architecture.demoarchitecture.databinding.RvItemReposBinding
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos

class GithubReposAdapter : RecyclerView.Adapter<GithubReposAdapter.ReposViewHolder>() {

    var reposList : MutableList<GithubRepos> = ArrayList<GithubRepos>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        var rvItemReposBinding :  RvItemReposBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.rv_item_repos, parent, false)

        return ReposViewHolder(rvItemReposBinding)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    override fun getItemCount(): Int = reposList.size

    fun updateResults(reposList : MutableList<GithubRepos>){
        this.reposList = reposList
        notifyDataSetChanged()
    }

    class ReposViewHolder(val binding: RvItemReposBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repos: GithubRepos){
            binding.githubRepos = repos
            binding.executePendingBindings()
        }
    }
}