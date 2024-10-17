package com.picpay.desafio.android.feature.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.common.dispatcher.Dispatcher
import com.picpay.desafio.android.common.dispatcher.PicPayDispatchers
import com.picpay.desafio.android.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: UserRepository,
    @Dispatcher(PicPayDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableLiveData<ContactListUiState>()
    val uiState: LiveData<ContactListUiState> = _uiState

    init {
        getUsers()
    }

    fun getUsers() {
        _uiState.value = ContactListUiState.Loading
        viewModelScope.launch(dispatcher) {
            when (val result = repository.getUsers()) {
                is Result.Success -> _uiState.postValue(ContactListUiState.Success(result.data))
                is Result.Error -> _uiState.postValue(ContactListUiState.Error(result.exception))
            }
        }
    }
}
