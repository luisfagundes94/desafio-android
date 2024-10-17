package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.model.UserEntity
import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.domain.model.User

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
