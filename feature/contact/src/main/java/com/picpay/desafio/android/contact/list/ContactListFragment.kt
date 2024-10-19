package com.picpay.desafio.android.contact.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.common.base.BaseFragment
import com.picpay.desafio.android.contact.adapter.ContactListAdapter
import com.picpay.desafio.android.contact.databinding.FragmentContactListBinding
import com.picpay.desafio.android.domain.model.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListFragment : BaseFragment<FragmentContactListBinding>() {

    private lateinit var adapter: ContactListAdapter

    private val viewModel: ContactListViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContactListBinding {
        return FragmentContactListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpAdapter()
        setUpObservers()
    }

    private fun setUpListeners() = with(binding) {
        sortButton.setOnClickListener { viewModel.sortContactList() }
    }

    private fun setUpAdapter() = with(binding) {
        adapter = ContactListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
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
