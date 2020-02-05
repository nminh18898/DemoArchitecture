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
import android.widget.Toast;

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

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }


    private void init(){
        initViewModel();
        setupRecyclerViewAndAdapter();
        setupBindingData();
        observeViewModel();
    }

    private void setupRecyclerViewAndAdapter(){
        adapter = new GithubReposAdapter();
        binding.rvRepos.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRepos.setAdapter(adapter);
        
        binding.rvRepos.addOnItemTouchListener(new GithubReposAdapterListener(this, binding.rvRepos, new GithubReposAdapterListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long clicked: " + position, Toast.LENGTH_SHORT).show();
            }

        }));
    }


    private void setupBindingData(){
        binding.setSearchViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void initViewModel(){

        GithubApi githubApi = RetrofitClient.getClient().create(GithubApi.class);
        DataRepository dataRepository = new DataRepository(githubApi);

        viewModel = new SearchViewModel(dataRepository);

    }

    private void observeViewModel(){
        observeDataList();
        observeErrorMessage();
    }

    private void observeDataList(){
        Observer<List<GithubRepos>> reposListObserver = new Observer<List<GithubRepos>>() {
            @Override
            public void onChanged(List<GithubRepos> reposList) {
                adapter.updateResults(reposList);
            }
        };
        viewModel.getReposListLiveData().observe(this, reposListObserver);
    }

    private void observeErrorMessage(){
        Observer<DataRepository.Error> errorObserver = new Observer<DataRepository.Error>() {
            @Override
            public void onChanged(DataRepository.Error error) {
                handleErrorWhenLoadingGithubRepos(error);
            }
        };

        viewModel.getErrorMessage().observe(this, errorObserver);
    }

    private void handleErrorWhenLoadingGithubRepos(DataRepository.Error error){
        switch (error.getCode()){
            case INVALID_QUERY:
                binding.etSearchQuery.setError("Your query is invalid");
                break;

            case NETWORK_ERROR:
                Toast.makeText(this, "Network Error! Check your connection", Toast.LENGTH_SHORT).show();
                break;

            case RESPONSE_FAILED:
                Toast.makeText(this, "Response error!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
