package com.nhatminh.example.architecture.demoarchitecture.search.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository;
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApi;
import com.nhatminh.example.architecture.demoarchitecture.repository.RetrofitClient;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenter;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Ref: https://android.jlelse.eu/the-real-beginner-guide-to-android-unit-testing-3859d2f25186
 */

public class MainActivity extends AppCompatActivity implements SearchViewContract{

    private static final String TAG = MainActivity.class.getSimpleName();

    SearchPresenterContract presenter;

    GithubReposAdapter adapter;

    EditText etSearchQuery;
    Button btSearch;
    RecyclerView rvRepos;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        setupViews();
        initPresenter();
        setupRecyclerViewAndAdapter();
    }

    private void setupRecyclerViewAndAdapter(){
        initPresenter();
        adapter = new GithubReposAdapter();
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(adapter);

        rvRepos.addOnItemTouchListener(new GithubReposAdapterListener(this, rvRepos, new GithubReposAdapterListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Click: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long click: " + position, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void setupViews(){
        etSearchQuery = findViewById(R.id.etSearchQuery);
        btSearch = findViewById(R.id.btSearch);
        rvRepos = findViewById(R.id.rvRepos);
        pbLoading = findViewById(R.id.pbLoading);

        pbLoading.setVisibility(View.GONE);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearchQuery.getText().toString();

                presenter.searchGithubRepos(query);
            }
        });
    }

    private void initPresenter(){

        GithubApi githubApi = RetrofitClient.getClient().create(GithubApi.class);
        DataRepository dataRepository = new DataRepository(githubApi);

        presenter = new SearchPresenter(this, dataRepository);
    }


    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void displayInputError() {
        etSearchQuery.setError("Invalid input");
    }

    @Override
    public void displaySearchedGithubRepos(List<GithubRepos> reposList) {
        adapter.updateResults(reposList);
    }

    @Override
    public void displayConnectionError() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySearchError(String errorMessage) {
        Toast.makeText(this, "Search Error !!!", Toast.LENGTH_SHORT).show();
    }
}
