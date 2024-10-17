package com.luisfagundes.data.repository

import com.luisfagundes.common.Result
import com.luisfagundes.data.database.UserDao
import com.luisfagundes.data.model.fakeUserEntityList
import com.luisfagundes.data.model.fakeUserList
import com.luisfagundes.data.model.fakeUserResponseList
import com.luisfagundes.data.service.PicPayService
import com.luisfagundes.domain.repository.UserRepository
import io.mockk.Called
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
    fun `getUsers returns users from database when available`() = kotlinx.coroutines.test.runTest {
        coEvery { userDao.getAllUsers() } returns fakeUserEntityList

        val result = userRepository.getUsers()

        coVerify(exactly = 1) { userDao.getAllUsers() }
        coVerify { service wasNot Called }
        assertTrue(result is Result.Success)
        assertEquals(fakeUserList, (result as Result.Success).data)
    }

    @Test
    fun `getUsers fetches from service when database is empty`() = kotlinx.coroutines.test.runTest {
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
    fun `getUsers returns error when exception occurs in database`() =
        kotlinx.coroutines.test.runTest {
            val exception = Exception("Database Error")
            coEvery { userDao.getAllUsers() } throws exception

            val result = userRepository.getUsers()

            coVerify { userDao.getAllUsers() }
            coVerify { service wasNot Called }
            assertTrue(result is Result.Error)
            assertEquals(exception, (result as Result.Error).exception)
        }

    @Test
    fun `getUsers returns error when exception occurs in service`() =
        kotlinx.coroutines.test.runTest {
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
