package com.yts.ytscleanarchitecture.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.yts.domain.entity.Book
import com.yts.domain.usecase.search.GetKeywordsUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class SearchViewModel(
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel() {
    private var _query = MutableLiveData<String>() //two way binding
    val query: LiveData<String> get() = _query

    fun setQuery(query: String) {
        _query.value = query
    }

    fun getKeywords() {
        addDisposable(
            getKeywordsUseCase.invoke(
                getTokenUseCase.invoke(),
                query = query.value ?: ""
            ), onSuccess = {

            }, onError = {
                commonError(it)
            }
        )
    }
}