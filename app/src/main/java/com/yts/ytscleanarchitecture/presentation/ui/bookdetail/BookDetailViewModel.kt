package com.yts.ytscleanarchitecture.presentation.ui.bookdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yts.domain.entity.Book
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class BookDetailViewModel : BaseViewModel() {

    private var _book = MutableLiveData<Book>()
    private var _position = MutableLiveData<Int>()

    val book: LiveData<Book> get() = _book
    val position: LiveData<Int> get() = _position


    fun setBookAndPosition(book: Book, position: Int) {
        _book.value = book
        _position.value = position
    }
}