package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.data.database.UserDao
import com.picpay.desafio.android.data.mapper.UserMapper.toDomain
import com.picpay.desafio.android.data.mapper.UserMapper.toEntity
import com.picpay.desafio.android.data.service.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: PicPayService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val usersFromDb = userDao.getAllUsers().map { it.toDomain() }

            if (usersFromDb.isNotEmpty()) {
                Result.Success(usersFromDb)
            } else {
                val response = service.getUsers()
                val data = response.map { it.toDomain() }

                userDao.clearUsers()
                userDao.insertUsers(response.map { it.toEntity() })
                Result.Success(data)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
