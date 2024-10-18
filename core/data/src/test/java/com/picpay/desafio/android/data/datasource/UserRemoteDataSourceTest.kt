package com.picpay.desafio.android.data.datasource

import com.picpay.desafio.android.data.datasource.remote.ContactRemoteDataSource
import com.picpay.desafio.android.data.datasource.remote.ContactRemoteDataSourceImpl
import com.picpay.desafio.android.data.model.fakeContactResponseList
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

    private lateinit var contactRemoteDataSource: ContactRemoteDataSource

    @Before
    fun setUp() {
        contactRemoteDataSource = ContactRemoteDataSourceImpl(service)
    }

    @Test
    fun `getContactList returns a list of contacts successfully`() = runTest {
        coEvery { service.getContactList() } returns fakeContactResponseList

        val result = contactRemoteDataSource.getContactList()

        assertEquals(fakeContactResponseList, result)
    }

    @Test
    fun `getContactList throws an exception when service fails`() = runTest {
        val exception = Exception("Network Error")
        coEvery { service.getContactList() } throws exception

        try {
            contactRemoteDataSource.getContactList()
        } catch (e: Exception) {
            assertTrue(true)
            assertEquals("Network Error", e.message)
        }
    }
}
