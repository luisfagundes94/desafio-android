package com.picpay.desafio.android.core.data.mapper

import com.picpay.desafio.android.core.data.model.UserEntity
import com.picpay.desafio.android.core.data.model.UserResponse
import com.picpay.desafio.android.core.domain.model.User

object UserMapper {
    fun UserResponse.toDomain() = User(
        id = id ?: 0,
        image = img ?: "",
        name = name ?: "",
        username = username ?: ""
    )

    fun UserEntity.toDomain() = User(
        id = this.id,
        image = this.image,
        name = this.name,
        username = this.username
    )

    fun UserResponse.toEntity() = UserEntity(
        id = this.id ?: 0,
        image = this.img ?: "",
        name = this.name ?: "",
        username = this.username ?: ""
    )
}
