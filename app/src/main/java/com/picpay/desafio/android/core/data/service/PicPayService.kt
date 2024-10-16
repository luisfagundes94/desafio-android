package com.picpay.desafio.android.core.data.service

import com.picpay.desafio.android.core.data.model.UserResponse
import retrofit2.http.GET


interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}