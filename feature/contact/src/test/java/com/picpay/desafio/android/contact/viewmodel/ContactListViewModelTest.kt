package com.picpay.desafio.android.contact.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.common.Result
import com.picpay.desafio.android.contact.list.ContactListUiState
import com.picpay.desafio.android.contact.list.ContactListViewModel
import com.picpay.desafio.android.domain.usecase.GetContactList
import com.picpay.desafio.android.testing.model.fakeContactList
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

    private val getContactList = mockk<GetContactList>()
    private val observer = mockk<Observer<ContactListUiState>>(relaxed = true)

    private lateinit var viewModel: ContactListViewModel

    @Before
    fun setUp() {
        coEvery { getContactList.invoke(any()) } returns Result.Success(emptyList())

        viewModel = ContactListViewModel(getContactList, testDispatcher)
        viewModel.uiState.observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.uiState.removeObserver(observer)
    }

    @Test
    fun `getContactList should update uiState with Success when repository returns Success`() {
        coEvery { getContactList.invoke(any()) } returns Result.Success(fakeContactList)

        viewModel.getContactList()

        verify {
            observer.onChanged(ContactListUiState.Loading)
            observer.onChanged(ContactListUiState.Success(fakeContactList))
        }

        viewModel.uiState.removeObserver(observer)
    }

    @Test
    fun `getContactList should update uiState with Error when repository returns Error`() {
        val exception = Exception("Error fetching contacts")
        coEvery { getContactList.invoke(any()) } returns Result.Error(exception)

        viewModel.getContactList()

        verify {
            observer.onChanged(ContactListUiState.Loading)
            observer.onChanged(ContactListUiState.Error(exception))
        }

        viewModel.uiState.removeObserver(observer)
    }
}
