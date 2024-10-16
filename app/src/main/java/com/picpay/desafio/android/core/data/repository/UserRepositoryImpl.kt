package com.picpay.desafio.android.core.data.repository

import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.data.mapper.UserMapper.toDomain
import com.picpay.desafio.android.core.data.service.PicPayService
import com.picpay.desafio.android.core.domain.model.User
import com.picpay.desafio.android.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: PicPayService
): UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        try {
            val response = service.getUsers()
            val data = response.map { it.toDomain() }
            return Result.Success(data)
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }
}