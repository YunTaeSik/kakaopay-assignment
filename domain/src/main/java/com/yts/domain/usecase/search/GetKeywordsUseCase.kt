package com.yts.domain.usecase.search

import com.yts.domain.repository.SearchRepository
import io.reactivex.Observable

class GetKeywordsUseCase(private val searchRepository: SearchRepository) {
    private val page = 1
    private val size = 50

    fun invoke(
        token: String,
        query: String
    ): Observable<*> {
        return searchRepository.getKeywords(token, query, null, page, size, null)
    }

}