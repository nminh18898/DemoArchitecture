package com.nhatminh.example.architecture.demoarchitecture.search.view

import android.os.Bundle
import android.text.Layout
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

class MainActivity : AppCompatActivity(), LifecycleOwner{

    lateinit var viewModel : SearchViewModel
    var adapter : GithubReposAdapter = GithubReposAdapter()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    private fun init() {
        initViewModel()
        setupRecyclerViewAndAdapter()
        setupBindingData()
    }

    private fun initViewModel() {
        var githubApi = RetrofitClient.retrofit.create(GithubApi::class.java)
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
}