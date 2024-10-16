package com.picpay.desafio.android.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.common.dispatcher.Dispatcher
import com.picpay.desafio.android.core.common.dispatcher.PicPayDispatchers
import com.picpay.desafio.android.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    @Dispatcher(PicPayDispatchers.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableLiveData<MainUiState>()
    val uiState: LiveData<MainUiState> = _uiState

    init {
        getUsers()
    }

    fun getUsers() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch(dispatcher) {
            when (val result = repository.getUsers()) {
                is Result.Success -> _uiState.postValue(MainUiState.Success(result.data))
                is Result.Error -> _uiState.postValue(MainUiState.Error(result.exception))
            }
        }
    }
}