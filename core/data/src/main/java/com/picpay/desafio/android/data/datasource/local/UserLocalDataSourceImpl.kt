package com.picpay.desafio.android.data.datasource.local

import com.picpay.desafio.android.data.database.UserDao
import com.picpay.desafio.android.data.model.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    override suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()
    override suspend fun insertUsers(users: List<UserEntity>) = userDao.insertUsers(users)
    override suspend fun clearUsers() = userDao.clearUsers()
}
