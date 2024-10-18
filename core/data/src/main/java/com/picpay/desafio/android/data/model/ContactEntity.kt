package com.picpay.desafio.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: Int,
    val image: String,
    val name: String,
    val username: String
)
