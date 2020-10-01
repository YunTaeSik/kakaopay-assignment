package com.yts.ytscleanarchitecture.presentation.ui.books

import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.yts.domain.entity.Book

interface OnBooksAdapterListener {
    fun gotoDetailBook(book: Book, position: Int, extras:    FragmentNavigator.Extras)
}