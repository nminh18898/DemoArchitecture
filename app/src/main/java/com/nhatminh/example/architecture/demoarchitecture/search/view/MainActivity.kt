package com.nhatminh.example.architecture.demoarchitecture.search.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhatminh.example.architecture.demoarchitecture.R
import com.nhatminh.example.architecture.demoarchitecture.databinding.ActivityMainBinding
import com.nhatminh.example.architecture.demoarchitecture.model.GithubRepos
import com.nhatminh.example.architecture.demoarchitecture.model.ReposData
import com.nhatminh.example.architecture.demoarchitecture.repository.DataRepository
import com.nhatminh.example.architecture.demoarchitecture.repository.GithubApi
import com.nhatminh.example.architecture.demoarchitecture.repository.RetrofitClient
import com.nhatminh.example.architecture.demoarchitecture.search.viewmodel.SearchViewModel
import com.nhatminh.example.architecture.demoarchitecture.search.viewmodel.SearchViewModelFactory
import com.nhatminh.example.architecture.demoarchitecture.model.STATE


class MainActivity : AppCompatActivity(), LifecycleOwner{

    private lateinit var viewModel : SearchViewModel
    private var adapter : GithubReposAdapter = GithubReposAdapter()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    private fun init() {
        initViewModel()
        setupRecyclerViewAndAdapter()
        setupBindingData()
        observerDataList()
    }

    private fun initViewModel() {
        val githubApi = RetrofitClient.retrofit.create(GithubApi::class.java)
        val repository = DataRepository(githubApi)
        viewModel = ViewModelProviders.of(this, SearchViewModelFactory(repository)).get(SearchViewModel::class.java)
    }

    private fun setupRecyclerViewAndAdapter() {
        binding.rvRepos.layoutManager = LinearLayoutManager(this)
        binding.rvRepos.adapter = adapter
    }

    private fun setupBindingData() {
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun observerDataList(){
        val reposListObserver : Observer<ReposData> = Observer {
            reposData -> adapter.updateResults(reposData.reposList)
            adapter.notifyDataSetChanged()
            handleError(reposData.state)
        }
        viewModel.reposData.observe(this, reposListObserver)
    }

    private fun handleError(state : STATE){
        when(state){
            STATE.ERROR_NETWORK -> showToast("Network error")
            STATE.ERROR_INVALID_QUERY -> binding.etSearchQuery.error = "Invalid query"
            STATE.ERROR_FAILED_RESPONSE -> showToast("Failed response")
            STATE.ERROR_UNKNOWN -> showToast("Something went wrong")
        }
    }

    private fun showToast(message : String){
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}