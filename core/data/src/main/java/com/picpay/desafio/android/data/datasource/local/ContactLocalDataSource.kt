package com.picpay.desafio.android.data.datasource.local

import com.picpay.desafio.android.data.model.ContactEntity

interface ContactLocalDataSource {
    suspend fun getContactList(): List<ContactEntity>
    suspend fun insertContactList(users: List<ContactEntity>)
    suspend fun clearContactList()
}
