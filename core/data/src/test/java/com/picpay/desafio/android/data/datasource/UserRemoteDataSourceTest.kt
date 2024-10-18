package com.picpay.desafio.android.data.datasource

import com.picpay.desafio.android.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.data.datasource.remote.UserRemoteDataSourceImpl
import com.picpay.desafio.android.data.model.fakeUserResponseList
import com.picpay.desafio.android.data.service.PicPayService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest {

    private val service: PicPayService = mockk()

    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun setUp() {
        userRemoteDataSource = UserRemoteDataSourceImpl(service)
    }

    @Test
    fun `getUsers returns a list of users successfully`() = runTest {
        coEvery { service.getUsers() } returns fakeUserResponseList

        val result = userRemoteDataSource.getUsers()

        assertEquals(fakeUserResponseList, result)
    }

    @Test
    fun `getUsers throws an exception when service fails`() = runTest {
        val exception = Exception("Network Error")
        coEvery { service.getUsers() } throws exception

        try {
            userRemoteDataSource.getUsers()
        } catch (e: Exception) {
            assertTrue(true)
            assertEquals("Network Error", e.message)
        }
    }
}
