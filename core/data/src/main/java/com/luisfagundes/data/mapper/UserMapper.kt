package com.luisfagundes.data.mapper

import com.luisfagundes.data.model.UserEntity
import com.luisfagundes.data.model.UserResponse
import com.luisfagundes.domain.model.User

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
