package com.yts.domain.usecase.search

import com.yts.domain.repository.SearchRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

class GetBooksUseCase(private val searchRepository: SearchRepository) {
    private val page = 1
    private val size = 50
    fun invoke(
        viewModelScope: CoroutineScope,
        token: String,
        query: String
    ): Observable<*> {
        return searchRepository.getBooks(viewModelScope, token, query, null, page, size, null)
    }
}