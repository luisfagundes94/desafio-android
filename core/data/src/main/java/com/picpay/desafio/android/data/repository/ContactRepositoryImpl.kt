package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.data.datasource.local.ContactLocalDataSource
import com.picpay.desafio.android.data.datasource.remote.ContactRemoteDataSource
import com.picpay.desafio.android.data.mapper.ContactMapper.toDomain
import com.picpay.desafio.android.data.mapper.ContactMapper.toEntity
import com.picpay.desafio.android.domain.model.Contact
import com.picpay.desafio.android.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val remoteDataSource: ContactRemoteDataSource,
    private val localDataSource: ContactLocalDataSource
) : ContactRepository {
    override suspend fun getContactList(): Result<List<Contact>> {
        return try {
            val contactsFromDb = localDataSource.getContactList().map { it.toDomain() }

            if (contactsFromDb.isNotEmpty()) {
                Result.Success(contactsFromDb)
            } else {
                val response = remoteDataSource.getContactList()
                val data = response.map { it.toDomain() }

                localDataSource.clearContactList()
                localDataSource.insertContactList(response.map { it.toEntity() })

                Result.Success(data)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
