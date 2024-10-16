package com.picpay.desafio.android.repository

import com.picpay.desafio.android.core.data.mapper.UserMapper.toDomain
import com.picpay.desafio.android.core.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.core.data.service.PicPayService
import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.domain.repository.UserRepository
import com.picpay.desafio.android.fake.model.fakeUserResponseList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private val service: PicPayService = mockk()
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(service)
    }

    @Test
    fun `getUsers returns Success when service returns data`() = runTest {
        val expectedUsers = fakeUserResponseList.map { it.toDomain() }
        coEvery { service.getUsers() } returns fakeUserResponseList

        val result = userRepository.getUsers()

        assertTrue(result is Result.Success)
        assertEquals(expectedUsers, (result as Result.Success).data)
        coVerify(exactly = 1) { service.getUsers() }
    }

    @Test
    fun `getUsers returns Error when service throws exception`() = runTest {
        val exception = Exception("Network error")
        coEvery { service.getUsers() } throws exception

        val result = userRepository.getUsers()

        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
        coVerify(exactly = 1) { service.getUsers() }
    }
}
