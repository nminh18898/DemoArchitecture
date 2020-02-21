package com.nhatminh.example.architecture.demoarchitecture.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhatminh.example.architecture.demoarchitecture.App;
import com.nhatminh.example.architecture.demoarchitecture.R;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Ref: https://android.jlelse.eu/the-real-beginner-guide-to-android-unit-testing-3859d2f25186
 */

public class MainActivity extends AppCompatActivity implements SearchViewContract{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    public SearchPresenterContract presenter;

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

        /*GithubApiService githubApiService = RetrofitClient.getClient().create(GithubApiService.class);
        DataRepository dataRepository = new DataRepository(githubApiService);

        presenter = new SearchPresenter(dataRepository);*/

        App.getComponent().inject(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void displayInputEmpty() {
        etSearchQuery.setError("This field is required");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
