package com.picpay.desafio.android

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.picpay.desafio.android.contact.R as contactR
import com.picpay.desafio.android.contact.list.ContactListActivity
import com.picpay.desafio.android.contact.list.ContactListUiState
import com.picpay.desafio.android.contact.list.ContactListViewModel
import com.picpay.desafio.android.designsystem.R as designSystemR
import com.picpay.desafio.android.domain.model.User
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContactListActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val viewModel: ContactListViewModel = mockk(relaxed = true)

    val userList = listOf(
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
        launchContactListActivity()

        // Check if title is displayed no matter the state
        onView(withId(contactR.id.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoadingStateIsDisplayed() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Loading)

        launchContactListActivity()

        // Check that the ProgressBar (Loading View) is visible
        onView(withId(designSystemR.id.progress_bar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSuccessStateIsDisplayed() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Success(userList))

        launchContactListActivity()

        // Check that the RecyclerView (Success View) is visible
        onView(withId(contactR.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testErrorStateIsDisplayed() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Error(Throwable()))

        launchContactListActivity()

        // Check that the error message and retry button are visible
        onView(withId(designSystemR.id.error_message))
            .check(matches(isDisplayed()))

        onView(withId(designSystemR.id.retry_button))
            .check(matches(isDisplayed()))
            .perform(click()) // Simulate a retry button click
    }

    private fun launchContactListActivity() {
        ActivityScenario.launch(ContactListActivity::class.java)
    }
}
