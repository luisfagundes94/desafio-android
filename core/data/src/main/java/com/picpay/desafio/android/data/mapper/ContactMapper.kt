package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.model.ContactEntity
import com.picpay.desafio.android.data.model.ContactResponse
import com.picpay.desafio.android.domain.model.Contact

object ContactMapper {
    fun ContactResponse.toDomain() = Contact(
        id = id ?: 0,
        image = img ?: "",
        name = name ?: "",
        username = username ?: ""
    )

    fun ContactEntity.toDomain() = Contact(
        id = this.id,
        image = this.image,
        name = this.name,
        username = this.username
    )

    fun ContactResponse.toEntity() = ContactEntity(
        id = this.id ?: 0,
        image = this.img ?: "",
        name = this.name ?: "",
        username = this.username ?: ""
    )
}
