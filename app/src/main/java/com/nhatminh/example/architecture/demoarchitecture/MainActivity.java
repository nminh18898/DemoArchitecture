package com.nhatminh.example.architecture.demoarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos;
import com.nhatminh.example.architecture.demoarchitecture.model.SearchResponse;
import com.nhatminh.example.architecture.demoarchitecture.network.GithubApi;
import com.nhatminh.example.architecture.demoarchitecture.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Ref: https://android.jlelse.eu/the-real-beginner-guide-to-android-unit-testing-3859d2f25186
 */

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    GithubApi githubApi;

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
        setupComponents();
    }

    private void setupComponents(){
        initGithubApi();
        adapter = new GithubReposAdapter();
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(adapter);
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
                if (!query.isEmpty()){
                    searchGithubRepos(query);
                }

            }
        });
    }

    private void initGithubApi(){
        githubApi = RetrofitClient.getClient().create(GithubApi.class);
    }

    private void searchGithubRepos(String query){
        pbLoading.setVisibility(View.VISIBLE);

        githubApi.searchRepos(query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                handleResponse(response);
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e(TAG, "Github Api request failed");
            }
        });

    }

    private void handleResponse(Response<SearchResponse> response) {
        if (response.isSuccessful()) {

            SearchResponse searchResponse = response.body();

            if (searchResponse != null) {

                handleSearchResults(searchResponse.getSearchResults());

            } else {

                handleError("Error! Data empty");

            }

        } else {

            handleError("Error! Not get response");

        }
    }

    private void handleSearchResults(List<GithubRepos> searchResults) {
        adapter.updateResults(searchResults);
    }

    private void handleError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


}
