package com.luisfagundes.domain.repository

import com.luisfagundes.common.Result
import com.luisfagundes.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}
