package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.domain.model.Contact
import com.picpay.desafio.android.domain.repository.ContactRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetContactListTest {

    private val contactRepository: ContactRepository = mockk()

    private lateinit var getContactList: GetContactList

    private val fakeContactList = listOf(
        Contact(id = 1, name = "John Doe", username = "", image = ""),
        Contact(id = 2, name = "Alice Smith", username = "", image = ""),
        Contact(id = 3, name = "Bob Johnson", username = "", image = "")
    )

    @Before
    fun setUp() {
        getContactList = GetContactList(contactRepository)
    }

    @Test
    fun `invoke returns sorted list when sortAlphabetically is true`() = runTest {
        coEvery { contactRepository.getContactList() } returns Result.Success(fakeContactList)

        val result = getContactList(sortAlphabetically = true)

        val sortedList = (result as Result.Success).data
        val sortedNames = listOf("Alice Smith", "Bob Johnson", "John Doe")

        assertEquals(sortedNames, sortedList.map { it.name })
    }

    @Test
    fun `invoke returns unsorted list when sortAlphabetically is false`() = runTest {
        coEvery { contactRepository.getContactList() } returns Result.Success(fakeContactList)

        val result = getContactList(sortAlphabetically = false)

        val contactList = (result as Result.Success).data
        assertEquals(fakeContactList, contactList) // Ensure it's the same order as the original
    }

    @Test
    fun `invoke returns error when repository throws exception`() = runTest {
        val exception = Exception("Repository error")
        coEvery { contactRepository.getContactList() } throws exception

        val result = getContactList(sortAlphabetically = false)

        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `invoke returns error when repository returns Result Error`() = runTest {
        val exception = Exception("Repository error")
        coEvery { contactRepository.getContactList() } returns Result.Error(exception)

        val result = getContactList(sortAlphabetically = false)

        assertEquals(exception, (result as Result.Error).exception)
    }
}
