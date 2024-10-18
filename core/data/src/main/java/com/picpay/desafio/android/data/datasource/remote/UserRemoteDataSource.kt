package com.picpay.desafio.android.data.datasource.remote

import com.picpay.desafio.android.data.model.UserResponse

interface UserRemoteDataSource {
    suspend fun getUsers(): List<UserResponse>
}
