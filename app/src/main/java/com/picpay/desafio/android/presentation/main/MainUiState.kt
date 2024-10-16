package com.picpay.desafio.android.presentation.main

import com.picpay.desafio.android.core.domain.model.User

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val data: List<User>) : MainUiState()
    data class Error(val exception: Throwable) : MainUiState()
}