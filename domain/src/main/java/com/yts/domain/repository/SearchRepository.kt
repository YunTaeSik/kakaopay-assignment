package com.yts.domain.repository

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope


interface SearchRepository {
    /**
     * query = 	검색을 원하는 질의어
     * sort = 	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
     * page = 	결과 페이지 번호	(기본 1)	1-50 사이 Integer
     * size = 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
     * target = 검색 필드 제한사용 가능한 값: title(제목), isbn (ISBN), publisher(출판사), person(인명)
     */
    fun getBooks(
        viewModelScope: CoroutineScope,
        token: String,
        query: String,
        sort: String?,
        page: Int?,
        size: Int?,
        target: String?
    ): Observable<*>
}