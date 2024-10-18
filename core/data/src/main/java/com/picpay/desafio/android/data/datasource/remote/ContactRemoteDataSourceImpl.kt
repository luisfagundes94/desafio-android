package com.picpay.desafio.android.data.datasource.remote

import com.picpay.desafio.android.data.model.ContactResponse
import com.picpay.desafio.android.data.service.PicPayService
import javax.inject.Inject

class ContactRemoteDataSourceImpl @Inject constructor(
    private val service: PicPayService
) : ContactRemoteDataSource {
    override suspend fun getContactList(): List<ContactResponse> = service.getContactList()
}
