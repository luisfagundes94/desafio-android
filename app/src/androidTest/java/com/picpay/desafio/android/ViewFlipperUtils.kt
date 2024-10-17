package com.picpay.desafio.android

import android.view.View
import android.widget.ViewFlipper
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

fun setDisplayedChild(index: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(ViewFlipper::class.java)
        }

        override fun getDescription(): String {
            return "Set ViewFlipper displayed child to index $index"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val viewFlipper = view as ViewFlipper
            viewFlipper.displayedChild = index
        }
    }
}
