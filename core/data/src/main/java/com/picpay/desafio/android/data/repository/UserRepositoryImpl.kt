package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.data.datasource.local.UserLocalDataSource
import com.picpay.desafio.android.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.data.mapper.UserMapper.toDomain
import com.picpay.desafio.android.data.mapper.UserMapper.toEntity
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val usersFromDb = localDataSource.getAllUsers().map { it.toDomain() }

            if (usersFromDb.isNotEmpty()) {
                Result.Success(usersFromDb)
            } else {
                val response = remoteDataSource.getUsers()
                val data = response.map { it.toDomain() }

                localDataSource.clearUsers()
                localDataSource.insertUsers(response.map { it.toEntity() })

                Result.Success(data)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
