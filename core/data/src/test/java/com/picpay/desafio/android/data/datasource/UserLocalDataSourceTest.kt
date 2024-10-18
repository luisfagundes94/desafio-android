package com.picpay.desafio.android.data.datasource

import com.picpay.desafio.android.data.database.ContactDao
import com.picpay.desafio.android.data.datasource.local.ContactLocalDataSourceImpl
import com.picpay.desafio.android.data.model.fakeContactEntityList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserLocalDataSourceTest {

    private val contactDao: ContactDao = mockk()

    private lateinit var userLocalDataSource: ContactLocalDataSourceImpl

    @Before
    fun setUp() {
        userLocalDataSource = ContactLocalDataSourceImpl(contactDao)
    }

    @Test
    fun `getContactList returns a list of contacts from database`() = runTest {
        coEvery { contactDao.getAll() } returns fakeContactEntityList

        val result = userLocalDataSource.getContactList()

        assertEquals(fakeContactEntityList, result)

        coVerify(exactly = 1) { contactDao.getAll() }
    }

    @Test
    fun `insertContactList inserts a list of contacts into the database`() = runTest {
        coEvery { contactDao.insertAll(fakeContactEntityList) } just runs

        userLocalDataSource.insertContactList(fakeContactEntityList)

        coVerify(exactly = 1) { contactDao.insertAll(fakeContactEntityList) }
    }

    @Test
    fun `clearContactList clears the contacts table in the database`() = runTest {
        coEvery { contactDao.clearAll() } just runs

        userLocalDataSource.clearContactList()

        coVerify(exactly = 1) { contactDao.clearAll() }
    }
}
