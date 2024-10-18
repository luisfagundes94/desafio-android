package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.data.datasource.local.ContactLocalDataSource
import com.picpay.desafio.android.data.datasource.remote.ContactRemoteDataSource
import com.picpay.desafio.android.data.model.fakeContactEntityList
import com.picpay.desafio.android.data.model.fakeContactResponseList
import com.picpay.desafio.android.domain.repository.ContactRepository
import com.picpay.desafio.android.testing.model.fakeContactList
import io.mockk.Called
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private val remoteDataSource: ContactRemoteDataSource = mockk()
    private val localDataSource: ContactLocalDataSource = mockk()

    private lateinit var contactRepository: ContactRepository

    @Before
    fun setUp() {
        contactRepository = ContactRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getContactList returns users from database when available`() = runTest {
        coEvery { localDataSource.getContactList() } returns fakeContactEntityList

        val result = contactRepository.getContactList()

        coVerify(exactly = 1) { localDataSource.getContactList() }
        coVerify { remoteDataSource wasNot Called }
        assertTrue(result is Result.Success)
        assertEquals(fakeContactList, (result as Result.Success).data)
    }

    @Test
    fun `getContactList fetches from service when database is empty`() = runTest {
        coEvery { localDataSource.getContactList() } returns emptyList()

        coEvery { remoteDataSource.getContactList() } returns fakeContactResponseList
        coEvery { localDataSource.clearContactList() } just Runs
        coEvery { localDataSource.insertContactList(fakeContactEntityList) } just Runs

        val result = contactRepository.getContactList()

        coVerifySequence {
            localDataSource.getContactList()
            remoteDataSource.getContactList()
            localDataSource.clearContactList()
            localDataSource.insertContactList(fakeContactEntityList)
        }
        assertTrue(result is Result.Success)
        assertEquals(fakeContactList, (result as Result.Success).data)
    }

    @Test
    fun `getContactList returns error when exception occurs in database`() = runTest {
        val exception = Exception("Database Error")
        coEvery { localDataSource.getContactList() } throws exception

        val result = contactRepository.getContactList()

        coVerify { localDataSource.getContactList() }
        coVerify { remoteDataSource wasNot Called }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `getContactList returns error when exception occurs in service`() = runTest {
        coEvery { localDataSource.getContactList() } returns emptyList()
        val exception = Exception("Service Error")
        coEvery { remoteDataSource.getContactList() } throws exception

        val result = contactRepository.getContactList()

        coVerifySequence {
            localDataSource.getContactList()
            remoteDataSource.getContactList()
        }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
