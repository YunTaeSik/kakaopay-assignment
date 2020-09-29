package com.yts.ytscleanarchitecture.presentation.ui.search

import androidx.lifecycle.MutableLiveData
import com.yts.domain.usecase.search.GetKeywordsUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.presentation.base.BaseViewModel

class SearchViewModel(
    private val getKeywordsUseCase: GetKeywordsUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel() {
    var query = MutableLiveData<String>() //two way binding

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