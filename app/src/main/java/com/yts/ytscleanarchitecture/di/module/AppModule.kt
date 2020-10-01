package com.yts.ytscleanarchitecture.di.module

import com.google.gson.Gson
import com.yts.ytscleanarchitecture.presentation.ui.bookdetail.BookDetailViewModel
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksAdapter
import com.yts.ytscleanarchitecture.presentation.ui.books.BooksViewModel
import com.yts.ytscleanarchitecture.presentation.ui.books.OnBooksAdapterListener
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var appModule = module {
    factory { (listener: OnBooksAdapterListener) -> BooksAdapter(listener) }

    viewModel {
        BooksViewModel(get(), get())
    }
    viewModel {
        BookDetailViewModel()
    }

    single { Gson() }

}

var moduleList = listOf(
    domainModule, appModule, dataModule
)