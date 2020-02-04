package com.nhatminh.example.architecture.demoarchitecture.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhatminh.example.architecture.demoarchitecture.R;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ReposViewHolder(inflater.inflate(R.layout.rv_item_repos, parent, false));
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

        TextView tvName, tvStars, tvDescription;

        public ReposViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_repo_name);
            tvStars = itemView.findViewById(R.id.tv_repo_star);
            tvDescription = itemView.findViewById(R.id.tv_repo_description);
        }

        public void bind(GithubRepos repos){
            tvName.setText(repos.getFullName());
            tvStars.setText("Stars: " + repos.getStargazersCount().toString());
            tvDescription.setText(repos.getDescription());
        }
    }
}