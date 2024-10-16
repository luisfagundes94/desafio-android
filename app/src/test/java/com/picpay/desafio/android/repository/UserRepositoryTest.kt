package com.picpay.desafio.android.repository

import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.data.database.UserDao
import com.picpay.desafio.android.core.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.core.data.service.PicPayService
import com.picpay.desafio.android.core.domain.repository.UserRepository
import com.picpay.desafio.android.fake.model.fakeUserEntityList
import com.picpay.desafio.android.fake.model.fakeUserList
import com.picpay.desafio.android.fake.model.fakeUserResponseList
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

    private val service = mockk<PicPayService>(relaxed = true)
    private val userDao = mockk<UserDao>(relaxed = true)

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(service, userDao)
    }

    @Test
    fun `getUsers returns users from database when available`() = runTest {
        coEvery { userDao.getAllUsers() } returns fakeUserEntityList

        val result = userRepository.getUsers()

        coVerify(exactly = 1) { userDao.getAllUsers() }
        coVerify { service wasNot Called }
        assertTrue(result is Result.Success)
        assertEquals(fakeUserList, (result as Result.Success).data)
    }

    @Test
    fun `getUsers fetches from service when database is empty`() = runTest {
        coEvery { userDao.getAllUsers() } returns emptyList()

        coEvery { service.getUsers() } returns fakeUserResponseList
        coEvery { userDao.clearUsers() } just Runs
        coEvery { userDao.insertUsers(fakeUserEntityList) } just Runs

        val result = userRepository.getUsers()

        coVerifySequence {
            userDao.getAllUsers()
            service.getUsers()
            userDao.clearUsers()
            userDao.insertUsers(fakeUserEntityList)
        }
        assertTrue(result is Result.Success)
        assertEquals(fakeUserList, (result as Result.Success).data)
    }

    @Test
    fun `getUsers returns error when exception occurs in database`() = runTest {
        val exception = Exception("Database Error")
        coEvery { userDao.getAllUsers() } throws exception

        val result = userRepository.getUsers()

        coVerify { userDao.getAllUsers() }
        coVerify { service wasNot Called }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `getUsers returns error when exception occurs in service`() = runTest {
        coEvery { userDao.getAllUsers() } returns emptyList()
        val exception = Exception("Service Error")
        coEvery { service.getUsers() } throws exception

        val result = userRepository.getUsers()

        coVerifySequence {
            userDao.getAllUsers()
            service.getUsers()
        }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
