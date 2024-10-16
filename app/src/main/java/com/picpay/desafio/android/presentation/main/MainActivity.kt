package com.picpay.desafio.android.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.domain.model.User
import com.picpay.desafio.android.core.extensions.hide
import com.picpay.desafio.android.core.extensions.show
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpAdapter()
        setUpObservers()

        viewModel.getUsers()
    }

    private fun setUpBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpAdapter() = with(binding) {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(this@MainActivity) { state ->
            when (state) {
                is MainUiState.Loading -> handleLoadingState()
                is MainUiState.Success -> handleSuccessState(state.data)
                is MainUiState.Error -> handleErrorState()
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.show()
    }

    private fun handleSuccessState(users: List<User>) = with(binding) {
        progressBar.hide()
        adapter.users = users
    }

    private fun handleErrorState() = with(binding) {
        progressBar.hide()
    }
}
