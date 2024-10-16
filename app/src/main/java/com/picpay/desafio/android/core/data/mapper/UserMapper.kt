package com.picpay.desafio.android.core.data.mapper

import com.picpay.desafio.android.core.data.model.UserResponse
import com.picpay.desafio.android.core.domain.model.User

object UserMapper {
    fun UserResponse.toDomain() = User(
        id = id ?: 0,
        image = img ?: "",
        name = name ?: "",
        username = username ?: ""
    )
}