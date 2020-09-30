package com.yts.ytscleanarchitecture.presentation.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yts.data.exception.PageDataEmptyException
import com.yts.domain.entity.Book
import com.yts.domain.usecase.search.GetBooksUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.R
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class BooksViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : BaseViewModel() {
    var query = MutableLiveData<String>()//two way

    private var _books = MutableLiveData<PagingData<Book>>()
    private var _booksType = MutableLiveData<BooksFragmentType>()

    val books: LiveData<PagingData<Book>> get() = _books
    val booksType: LiveData<BooksFragmentType> get() = _booksType

    private var searchDelayDisposable: Disposable? = null

    fun initQuery() {
        query.value = ""
    }

    @Suppress("UNCHECKED_CAST")
    fun getBooks(query: String) {
        searchDelayDisposable?.dispose()
        _isLoading.value = false

        if (query.isNotEmpty()) {
            searchDelayDisposable = addDisposable(Observable.concat(
                Observable.timer(1500, TimeUnit.MILLISECONDS),
                getBooksUseCase.invoke(
                    viewModelScope = viewModelScope,
                    token = getTokenUseCase.invoke(),
                    query
                )
            ), onSuccess = {
                if (it is PagingData<*>) {
                    _books.postValue(it as PagingData<Book>)
                    _booksType.postValue(BooksFragmentType.RESULT)
                }
            }, onError = {
                commonError(it)
            })
        } else {
            emptyBooks()
        }
    }

    private fun emptyBooks() {
        _books.value = PagingData.empty()
        _booksType.value = BooksFragmentType.EMPTY
    }

    fun loadError(throwable: Throwable) {
        if (throwable is PageDataEmptyException) {
            _books.value = PagingData.empty()
            _toastMessageId.postValue(R.string.error_query_size_null_message)
        } else {
            commonError(throwable)
        }
    }
}