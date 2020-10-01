package com.yts.ytscleanarchitecture.books

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksFragment
import kotlinx.android.synthetic.main.fragment_books.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BooksFragmentTest {

    @Test
    fun testNavigateToBooksFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_main)

        val booksScenario = launchFragmentInContainer<BooksFragment>(themeResId = R.style.AppTheme)

        booksScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        val currentDestination = navController.backStack.last()
        Truth.assertThat(currentDestination.destination.id).isEqualTo(R.id.booksFragment)
    }

    @Test
    fun testNavigateToBookDetailFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_main)

        val booksScenario = launchFragmentInContainer<BooksFragment>(themeResId = R.style.AppTheme)
        var booksFragment: BooksFragment? = null

        booksScenario.onFragment { fragment ->
            booksFragment = fragment
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.edit_search)).perform(ViewActions.replaceText("테스트"))

        onView(withId(R.id.edit_search)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "테스트"
                )
            )
        )
        booksFragment?.let {
            waitForAdapterChange(it.list_book)
        }
        val position = 1
        onView(withId(R.id.list_book))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, object : ViewAction {
                override fun getConstraints() = null
                override fun getDescription() = "Click on an item view with specified id."
                override fun perform(uiController: UiController?, view: View?) {
                    view?.performClick()
                }
            }))
        val currentDestination = navController.backStack.last()
        Truth.assertThat(currentDestination.destination.id).isEqualTo(R.id.bookDetailFragment)
    }

    @Throws(InterruptedException::class)
    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter!!.registerAdapterDataObserver(
                object : AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }

                    override fun onChanged() {
                        latch.countDown()
                    }
                })
        }
        if (recyclerView.adapter!!.itemCount > 0) {
            return  //already loaded
        }
        MatcherAssert.assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }
}
