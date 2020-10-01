package com.yts.ytscleanarchitecture.bookdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.LiveDataTestUtil
import com.yts.ytscleanarchitecture.di.module.moduleList
import com.yts.ytscleanarchitecture.presentation.ui.bookdetail.BookDetailViewModel
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class BookDetailViewModelTest : KoinTest {
    private val bookDetailViewModel: BookDetailViewModel by inject()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidLogger(Level.ERROR)
        modules(moduleList)
    }

    @Test
    fun setBookAndPosition() {
        val book = Book()
        val position = 1
        bookDetailViewModel.setBookAndPosition(book, position)

        Truth.assertThat(LiveDataTestUtil.getValue(bookDetailViewModel.book)).isEqualTo(book)
        Truth.assertThat(LiveDataTestUtil.getValue(bookDetailViewModel.position))
            .isEqualTo(position)
    }


}