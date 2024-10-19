package com.picpay.desafio.android

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.picpay.desafio.android.contact.list.ContactListFragment
import com.picpay.desafio.android.contact.list.ContactListUiState
import com.picpay.desafio.android.contact.list.ContactListViewModel
import com.picpay.desafio.android.domain.model.Contact
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.picpay.desafio.android.contact.R as contactR
import com.picpay.desafio.android.designsystem.R as designSystemR

@HiltAndroidTest
class ContactListFragmentTest : Fragment() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val viewModel: ContactListViewModel = mockk(relaxed = true)

    val contactList = listOf(
        Contact(
            id = 1,
            name = "Bob Williams",
            username = "bob",
            image = "https://avatars.githubusercontent.com/u/1?v=4"
        ),
        Contact(
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
    fun titleIsDisplayedInAnyState() {
        launchFragmentInHiltContainer<ContactListFragment>()

        onView(withId(contactR.id.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun sortButtonIsDisplayedInAnyState() {
        launchFragmentInHiltContainer<ContactListFragment>()

        onView(withId(contactR.id.sort_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadingStateDisplaysProgressBar() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Loading)

        launchFragmentInHiltContainer<ContactListFragment>()

        onView(withId(designSystemR.id.progress_bar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun successStateDisplaysRecyclerView() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Success(contactList))

        launchFragmentInHiltContainer<ContactListFragment>()

        onView(withId(contactR.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun errorStateDisplaysErrorMessageAndRetryButton() {
        every { viewModel.uiState } returns MutableLiveData(ContactListUiState.Error(Throwable()))

        launchFragmentInHiltContainer<ContactListFragment>()

        onView(withId(designSystemR.id.error_message))
            .check(matches(isDisplayed()))

        onView(withId(designSystemR.id.retry_button))
            .check(matches(isDisplayed()))
    }
}
