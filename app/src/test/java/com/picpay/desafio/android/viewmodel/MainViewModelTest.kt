package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.core.common.Result
import com.picpay.desafio.android.core.domain.repository.UserRepository
import com.picpay.desafio.android.fake.model.fakeUserList
import com.picpay.desafio.android.presentation.main.MainUiState
import com.picpay.desafio.android.presentation.main.MainViewModel
import com.picpay.desafio.android.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository = mockk<UserRepository>()
    private val observer = mockk<Observer<MainUiState>>(relaxed = true)

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        coEvery { repository.getUsers() } returns Result.Success(emptyList())

        viewModel = MainViewModel(repository, dispatcherRule.testDispatcher)
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
            observer.onChanged(MainUiState.Loading)
            observer.onChanged(MainUiState.Success(fakeUserList))
        }

        viewModel.uiState.removeObserver(observer)
    }

    @Test
    fun `getUsers should update uiState with Error when repository returns Error`() {
        val exception = Exception("Error fetching users")
        coEvery { repository.getUsers() } returns Result.Error(exception)

        viewModel.getUsers()

        verify {
            observer.onChanged(MainUiState.Loading)
            observer.onChanged(MainUiState.Error(exception))
        }

        viewModel.uiState.removeObserver(observer)
    }
}

