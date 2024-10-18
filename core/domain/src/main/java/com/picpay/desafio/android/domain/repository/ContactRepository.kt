package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.domain.model.Contact

interface ContactRepository {
    suspend fun getContactList(): Result<List<Contact>>
}
