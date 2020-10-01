package com.yts.ytscleanarchitecture.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.yts.ytscleanarchitecture.LiveDataTestUtil
import com.yts.ytscleanarchitecture.di.module.moduleList
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksFragmentType
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class BooksViewModelTest : KoinTest {
    private val booksViewModel: BooksViewModel by inject()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidLogger(Level.ERROR)
        modules(moduleList)
    }

    @Test
    fun initQuery() {
        booksViewModel.initQuery()
        assertThat(LiveDataTestUtil.getValue(booksViewModel.query)).isEmpty()
    }

    @Test
    fun loadBooks() {
        booksViewModel.getBooks("미움")
    }

}