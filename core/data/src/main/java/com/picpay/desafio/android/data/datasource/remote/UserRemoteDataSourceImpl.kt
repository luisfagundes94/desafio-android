package com.picpay.desafio.android.data.datasource.remote

import com.picpay.desafio.android.data.model.UserResponse
import com.picpay.desafio.android.data.service.PicPayService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val service: PicPayService
) : UserRemoteDataSource {
    override suspend fun getUsers(): List<UserResponse> = service.getUsers()
}
