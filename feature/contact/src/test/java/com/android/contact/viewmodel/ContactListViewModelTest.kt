package com.android.contact.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.feature.contact.ContactListUiState
import com.picpay.desafio.android.feature.contact.ContactListViewModel
import com.picpay.desafio.android.model.fakeUserList
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import java.lang.Exception
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val repository = mockk<UserRepository>()
    private val observer = mockk<Observer<ContactListUiState>>(relaxed = true)

    private lateinit var viewModel: ContactListViewModel

    @Before
    fun setUp() {
        coEvery { repository.getUsers() } returns Result.Success(emptyList())

        viewModel = ContactListViewModel(repository, testDispatcher)
        viewModel.uiState.observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.uiState.removeObserver(observer)
    }

    @Test
    fun `getUsers should update uiState with Success when repository returns Success`() {
        coEvery { repository.getUsers() } returns Result.Success(fakeUserList)

        viewModel.getUsers()

        verify {
            observer.onChanged(ContactListUiState.Loading)
            observer.onChanged(ContactListUiState.Success(fakeUserList))
        }

        viewModel.uiState.removeObserver(observer)
    }

    @Test
    fun `getUsers should update uiState with Error when repository returns Error`() {
        val exception = Exception("Error fetching users")
        coEvery { repository.getUsers() } returns Result.Error(exception)

        viewModel.getUsers()

        verify {
            observer.onChanged(ContactListUiState.Loading)
            observer.onChanged(ContactListUiState.Error(exception))
        }

        viewModel.uiState.removeObserver(observer)
    }
}
