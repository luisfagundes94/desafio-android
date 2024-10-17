package com.luisfagundes.data.repository

import com.luisfagundes.common.Result
import com.luisfagundes.data.database.UserDao
import com.luisfagundes.data.mapper.UserMapper.toDomain
import com.luisfagundes.data.mapper.UserMapper.toEntity
import com.luisfagundes.data.service.PicPayService
import com.luisfagundes.domain.model.User
import com.luisfagundes.domain.repository.UserRepository
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
