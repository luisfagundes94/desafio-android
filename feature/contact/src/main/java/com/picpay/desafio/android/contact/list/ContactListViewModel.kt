package com.picpay.desafio.android.contact.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.common.dispatcher.Dispatcher
import com.picpay.desafio.android.common.dispatcher.PicPayDispatchers
import com.picpay.desafio.android.domain.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactRepository,
    @Dispatcher(PicPayDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableLiveData<ContactListUiState>()
    val uiState: LiveData<ContactListUiState> = _uiState

    init {
        getContactList()
    }

    fun getContactList() {
        _uiState.value = ContactListUiState.Loading
        viewModelScope.launch(dispatcher) {
            when (val result = repository.getContactList()) {
                is Result.Success -> _uiState.postValue(ContactListUiState.Success(result.data))
                is Result.Error -> _uiState.postValue(ContactListUiState.Error(result.exception))
            }
        }
    }
}
