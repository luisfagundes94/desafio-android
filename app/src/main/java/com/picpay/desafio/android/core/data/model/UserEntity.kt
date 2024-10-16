package com.picpay.desafio.android.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val image: String,
    val name: String,
    val username: String
)