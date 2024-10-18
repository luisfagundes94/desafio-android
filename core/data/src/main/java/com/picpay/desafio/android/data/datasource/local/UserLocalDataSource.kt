package com.picpay.desafio.android.data.datasource.local

import com.picpay.desafio.android.data.model.UserEntity

interface UserLocalDataSource {
    suspend fun getAllUsers(): List<UserEntity>
    suspend fun insertUsers(users: List<UserEntity>)
    suspend fun clearUsers()
}
