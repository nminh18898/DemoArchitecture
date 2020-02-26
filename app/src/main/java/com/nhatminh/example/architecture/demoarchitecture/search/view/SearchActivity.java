package com.nhatminh.example.architecture.demoarchitecture.search.view;

import android.content.Intent;
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
import com.nhatminh.example.architecture.demoarchitecture.home.HomeActivity;
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.search.presenter.SearchPresenterContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Ref: https://android.jlelse.eu/the-real-beginner-guide-to-android-unit-testing-3859d2f25186
 */

public class SearchActivity extends AppCompatActivity implements SearchViewContract{

    private static final String TAG = SearchActivity.class.getSimpleName();

    @Inject
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
        adapter = new GithubReposAdapter();
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(adapter);

        rvRepos.addOnItemTouchListener(new GithubReposAdapterListener(this, rvRepos, new GithubReposAdapterListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.onSearchReposItemClicked(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                presenter.onSearchReposItemLongClicked(position);
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

    private void findLastSearchQuery(){
        presenter.retrieveLastQuery();
    }



    private void initPresenter(){

        /**
         *  == Initialization code without dagger ==
         *
        GithubApiService githubApiService = RetrofitClient.getClient().create(GithubApiService.class);
        DataRepository dataRepository = new DataRepository(githubApiService);

        presenter = new SearchPresenter(dataRepository); */

        // Initialization code with dagger
        App.getComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.saveLastQuery();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        findLastSearchQuery();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
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
        etSearchQuery.setError(getResources().getString(R.string.this_field_is_required));
    }

    @Override
    public void displayInputError() {
        etSearchQuery.setError(getResources().getString(R.string.invalid_input));
    }

    @Override
    public void displaySearchedGithubRepos(List<GithubRepos> reposList) {
        adapter.updateResults(reposList);
    }

    @Override
    public void displayConnectionError() {
        Toast.makeText(this, getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySearchError(String errorMessage) {
        Toast.makeText(this, getResources().getString(R.string.search_response_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHomeActivity() {
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayLastSearchQuery(String lastQuery) {
        etSearchQuery.setText(lastQuery);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
