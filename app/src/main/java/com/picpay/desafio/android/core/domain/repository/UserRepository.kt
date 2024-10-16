package com.picpay.desafio.android.core.domain.repository

import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}