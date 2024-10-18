package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.data.datasource.local.UserLocalDataSource
import com.picpay.desafio.android.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.data.model.fakeUserEntityList
import com.picpay.desafio.android.data.model.fakeUserResponseList
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.testing.model.fakeUserList
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
    private val remoteDataSource: UserRemoteDataSource = mockk()
    private val localDataSource: UserLocalDataSource = mockk()

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getUsers returns users from database when available`() = runTest {
        coEvery { localDataSource.getAllUsers() } returns fakeUserEntityList

        val result = userRepository.getUsers()

        coVerify(exactly = 1) { localDataSource.getAllUsers() }
        coVerify { remoteDataSource wasNot Called }
        assertTrue(result is Result.Success)
        assertEquals(fakeUserList, (result as Result.Success).data)
    }

    @Test
    fun `getUsers fetches from service when database is empty`() = runTest {
        coEvery { localDataSource.getAllUsers() } returns emptyList()

        coEvery { remoteDataSource.getUsers() } returns fakeUserResponseList
        coEvery { localDataSource.clearUsers() } just Runs
        coEvery { localDataSource.insertUsers(fakeUserEntityList) } just Runs

        val result = userRepository.getUsers()

        coVerifySequence {
            localDataSource.getAllUsers()
            remoteDataSource.getUsers()
            localDataSource.clearUsers()
            localDataSource.insertUsers(fakeUserEntityList)
        }
        assertTrue(result is Result.Success)
        assertEquals(fakeUserList, (result as Result.Success).data)
    }

    @Test
    fun `getUsers returns error when exception occurs in database`() = runTest {
        val exception = Exception("Database Error")
        coEvery { localDataSource.getAllUsers() } throws exception

        val result = userRepository.getUsers()

        coVerify { localDataSource.getAllUsers() }
        coVerify { remoteDataSource wasNot Called }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `getUsers returns error when exception occurs in service`() = runTest {
        coEvery { localDataSource.getAllUsers() } returns emptyList()
        val exception = Exception("Service Error")
        coEvery { remoteDataSource.getUsers() } throws exception

        val result = userRepository.getUsers()

        coVerifySequence {
            localDataSource.getAllUsers()
            remoteDataSource.getUsers()
        }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
