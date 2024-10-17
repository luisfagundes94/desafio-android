package com.luisfagundes.contact

import com.luisfagundes.domain.model.User

sealed class ContactListUiState {
    object Loading : ContactListUiState()
    data class Success(val data: List<User>) : ContactListUiState()
    data class Error(val exception: Throwable) : ContactListUiState()
}
