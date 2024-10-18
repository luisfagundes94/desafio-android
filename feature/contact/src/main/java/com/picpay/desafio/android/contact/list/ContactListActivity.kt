package com.picpay.desafio.android.contact.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.contact.R
import com.picpay.desafio.android.contact.adapter.ContactListAdapter
import com.picpay.desafio.android.contact.databinding.ActivityContactListBinding
import com.picpay.desafio.android.domain.model.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListActivity : AppCompatActivity(R.layout.activity_contact_list) {
    private lateinit var binding: ActivityContactListBinding
    private lateinit var adapter: ContactListAdapter

    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpAdapter()
        setUpObservers()
    }

    private fun setUpBinding() {
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpAdapter() = with(binding) {
        adapter = ContactListAdapter()
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

    private fun handleSuccessState(contactList: List<Contact>) = with(binding) {
        viewFlipper.displayedChild = SUCCESS_VIEW
        adapter.submitList(contactList)
    }

    private fun handleErrorState() = with(binding) {
        viewFlipper.displayedChild = ERROR_VIEW
        errorComponent.retryButton.setOnClickListener { viewModel.getContactList() }
    }

    companion object {
        const val SUCCESS_VIEW = 0
        const val LOADING_VIEW = 1
        const val ERROR_VIEW = 2
    }
}
