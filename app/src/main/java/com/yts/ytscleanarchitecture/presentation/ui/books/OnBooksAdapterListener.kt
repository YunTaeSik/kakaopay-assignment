package com.yts.ytscleanarchitecture.presentation.ui.books

import com.yts.domain.entity.Book

interface OnBooksAdapterListener {
    fun gotoDetailBook(book: Book, position: Int)
}