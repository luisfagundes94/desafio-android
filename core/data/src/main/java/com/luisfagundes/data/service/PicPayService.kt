package com.luisfagundes.data.service

import com.luisfagundes.data.model.UserResponse
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
