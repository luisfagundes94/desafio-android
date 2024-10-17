package com.picpay.desafio.android.feature.contact

import com.picpay.desafio.android.domain.model.User

sealed class ContactListUiState {
    object Loading : ContactListUiState()
    data class Success(val data: List<User>) : ContactListUiState()
    data class Error(val exception: Throwable) : ContactListUiState()
}
