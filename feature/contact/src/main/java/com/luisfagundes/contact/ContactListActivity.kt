package com.luisfagundes.contact

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisfagundes.contact.adapter.UserListAdapter
import com.luisfagundes.contact.databinding.ActivityMainBinding
import com.luisfagundes.domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpAdapter()
        setUpObservers()
    }

    private fun setUpBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpAdapter() = with(binding) {
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ContactListActivity)
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(this@ContactListActivity) { state ->
            when (state) {
                is ContactListUiState.Loading -> handleLoadingState()
                is ContactListUiState.Success -> handleSuccessState(state.data)
                is ContactListUiState.Error -> handleErrorState()
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        viewFlipper.displayedChild = LOADING_VIEW
    }

    private fun handleSuccessState(users: List<User>) = with(binding) {
        viewFlipper.displayedChild = SUCCESS_VIEW
        adapter.submitList(users)
    }

    private fun handleErrorState() = with(binding) {
        viewFlipper.displayedChild = ERROR_VIEW
        errorComponent.retryButton.setOnClickListener { viewModel.getUsers() }
    }

    companion object {
        const val SUCCESS_VIEW = 0
        const val LOADING_VIEW = 1
        const val ERROR_VIEW = 2
    }
}
