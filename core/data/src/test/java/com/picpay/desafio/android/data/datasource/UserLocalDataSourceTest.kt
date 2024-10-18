package com.picpay.desafio.android.data.datasource

import com.picpay.desafio.android.data.database.UserDao
import com.picpay.desafio.android.data.datasource.local.UserLocalDataSourceImpl
import com.picpay.desafio.android.data.model.fakeUserEntityList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserLocalDataSourceTest {

    private val userDao: UserDao = mockk()

    private lateinit var userLocalDataSource: UserLocalDataSourceImpl

    @Before
    fun setUp() {
        userLocalDataSource = UserLocalDataSourceImpl(userDao)
    }

    @Test
    fun `getAllUsers returns a list of users from database`() = runTest {
        coEvery { userDao.getAllUsers() } returns fakeUserEntityList

        val result = userLocalDataSource.getAllUsers()

        assertEquals(fakeUserEntityList, result)

        coVerify(exactly = 1) { userDao.getAllUsers() }
    }

    @Test
    fun `insertUsers inserts a list of users into the database`() = runTest {
        coEvery { userDao.insertUsers(fakeUserEntityList) } just runs

        userLocalDataSource.insertUsers(fakeUserEntityList)

        coVerify(exactly = 1) { userDao.insertUsers(fakeUserEntityList) }
    }

    @Test
    fun `clearUsers clears the user table in the database`() = runTest {
        coEvery { userDao.clearUsers() } just runs

        userLocalDataSource.clearUsers()

        coVerify(exactly = 1) { userDao.clearUsers() }
    }
}
