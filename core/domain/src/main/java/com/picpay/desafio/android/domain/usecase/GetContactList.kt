package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.repository.ContactRepository
import javax.inject.Inject

class GetContactList @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke() = contactRepository.getContactList()
}
