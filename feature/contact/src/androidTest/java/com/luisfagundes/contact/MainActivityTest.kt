package com.picpay.desafio.android

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.luisfagundes.domain.model.User
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val viewModel: com.luisfagundes.contact.MainViewModel = mockk(relaxed = true)

    val userList =
        listOf(
            User(
                id = 1,
                name = "Bob Williams",
                username = "bob",
                image = "https://avatars.githubusercontent.com/u/1?v=4"
            ),
            User(
                id = 2,
                name = "Sam Smith",
                username = "sam",
                image = "https://avatars.githubusercontent.com/u/2?v=4"
            )
        )

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testTitleIsDisplayed() {
        launchMainActivity()

        // Check if title is displayed no matter the state
        onView(withId(R.id.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoadingStateIsDisplayed() {
        every {
            com.luisfagundes.contact.MainViewModel.uiState
        } returns MutableLiveData(com.luisfagundes.contact.MainUiState.Loading)

        launchMainActivity()

        // Check that the ProgressBar (Loading View) is visible
        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSuccessStateIsDisplayed() {
        every { com.luisfagundes.contact.MainViewModel.uiState } returns MutableLiveData(
            com.luisfagundes.contact.MainUiState.Success(
                userList
            )
        )

        launchMainActivity()

        // Check that the RecyclerView (Success View) is visible
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testErrorStateIsDisplayed() {
        every { com.luisfagundes.contact.MainViewModel.uiState } returns MutableLiveData(
            com.luisfagundes.contact.MainUiState.Error(
                Throwable()
            )
        )

        launchMainActivity()

        // Check that the error message and retry button are visible
        onView(withId(R.id.error_message))
            .check(matches(isDisplayed()))

        onView(withId(R.id.retry_button))
            .check(matches(isDisplayed()))
            .perform(click()) // Simulate a retry button click
    }

    private fun launchMainActivity() {
        ActivityScenario.launch(com.luisfagundes.contact.MainActivity::class.java)
    }
}
