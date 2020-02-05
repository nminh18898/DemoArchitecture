package com.nhatminh.example.architecture.demoarchitecture.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.databinding.RvItemReposBinding;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;

import java.util.ArrayList;
import java.util.List;

public class GithubReposAdapter extends RecyclerView.Adapter<GithubReposAdapter.ReposViewHolder> {

    List<GithubRepos> reposList;


    public GithubReposAdapter() {
        reposList = new ArrayList<>();
    }

    public GithubReposAdapter(List<GithubRepos> reposList) {
        this.reposList = reposList;
    }

    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemReposBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_repos, parent, false);
        return new ReposViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        holder.bind(reposList.get(position));
    }



    @Override
    public int getItemCount() {
        return reposList.size();
    }

    void updateResults(List<GithubRepos> reposList) {
        this.reposList = reposList;
        notifyDataSetChanged();
    }

    static class ReposViewHolder extends RecyclerView.ViewHolder{

        RvItemReposBinding binding;

        public ReposViewHolder(RvItemReposBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(GithubRepos repos){
            binding.setGithubRepos(repos);
            binding.executePendingBindings();
        }
    }
}