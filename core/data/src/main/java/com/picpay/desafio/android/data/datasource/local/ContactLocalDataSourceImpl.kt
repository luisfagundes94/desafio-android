package com.picpay.desafio.android.data.datasource.local

import com.picpay.desafio.android.data.database.ContactDao
import com.picpay.desafio.android.data.model.ContactEntity
import javax.inject.Inject

class ContactLocalDataSourceImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactLocalDataSource {
    override suspend fun getContactList(): List<ContactEntity> = contactDao.getAll()
    override suspend fun insertContactList(users: List<ContactEntity>) = contactDao.insertAll(users)
    override suspend fun clearContactList() = contactDao.clearAll()
}
