package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.common.getOrThrow
import com.picpay.desafio.android.domain.model.Contact
import com.picpay.desafio.android.domain.repository.ContactRepository
import javax.inject.Inject

class GetContactList @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(sortAlphabetically: Boolean): Result<List<Contact>> {
        return try {
            val contactList = contactRepository.getContactList().getOrThrow()
            if (sortAlphabetically) {
                Result.Success(contactList.sortedBy { it.name })
            } else {
                Result.Success(contactList)
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}
