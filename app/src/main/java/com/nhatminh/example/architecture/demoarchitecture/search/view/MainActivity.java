package com.nhatminh.example.architecture.demoarchitecture.search.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.databinding.ActivityMainBinding;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApi;
import com.nhatminh.example.architecture.demoarchitecture.repository.RetrofitClient;
import com.nhatminh.example.architecture.demoarchitecture.search.viewmodel.SearchViewModel;

import java.util.List;

/**
 * Ref: https://android.jlelse.eu/the-real-beginner-guide-to-android-unit-testing-3859d2f25186
 */

public class MainActivity extends AppCompatActivity implements  LifecycleOwner {

    private static final String TAG = MainActivity.class.getSimpleName();

    SearchViewModel viewModel;

    GithubReposAdapter adapter;

    RecyclerView rvRepos;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }


    private void init(){
        setupViews();
        setupComponents();
        setupBindingData();
    }

    private void setupComponents(){
        initViewModel();

        adapter = new GithubReposAdapter();
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(adapter);
    }

    private void setupViews(){
        rvRepos = findViewById(R.id.rvRepos);
    }

    private void setupBindingData(){
        binding.setSearchViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initViewModel(){

        GithubApi githubApi = RetrofitClient.getClient().create(GithubApi.class);
        DataRepository dataRepository = new DataRepository(githubApi);

        Observer<List<GithubRepos>> reposListObserver = new Observer<List<GithubRepos>>() {
            @Override
            public void onChanged(List<GithubRepos> reposList) {
                adapter.updateResults(reposList);
            }
        };

        viewModel = new SearchViewModel(dataRepository);
        viewModel.getReposListLiveData().observe(this, reposListObserver);
    }

}
