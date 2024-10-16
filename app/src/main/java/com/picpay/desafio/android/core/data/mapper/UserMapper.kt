package com.picpay.desafio.android.core.data.mapper

import com.picpay.desafio.android.core.data.model.UserResponse
import com.picpay.desafio.android.core.domain.model.User

object UserMapper {
    fun UserResponse.toDomain() = User(
        id = id,
        image = img,
        name = name,
        username = username
    )

    fun User.toEntity() = UserResponse(
        id = id,
        img = image,
        name = name,
        username = username
    )
}