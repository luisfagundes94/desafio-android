package com.picpay.desafio.android.data.datasource.remote

import com.picpay.desafio.android.data.model.ContactResponse

interface ContactRemoteDataSource {
    suspend fun getContactList(): List<ContactResponse>
}
