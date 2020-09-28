package com.yts.ytscleanarchitecture.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yts.domain.entity.Book
import com.yts.domain.usecase.search.GetBooksUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class SearchBooksViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : BaseViewModel() {

    var query = MutableLiveData<String>() // two-way
    private var _books = MutableLiveData<PagingData<Book>>()

    val books: LiveData<PagingData<Book>> get() = _books

    @Suppress("UNCHECKED_CAST")
    fun getBooks(query: String) {
        addDisposable(
            getBooksUseCase.invoke(
                viewModelScope = viewModelScope,
                token = getTokenUseCase.invoke(),
                query
            ), onSuccess = {
                _books.value = it as PagingData<Book>
            }, onError = {
                commonError(it)
            })
    }
}