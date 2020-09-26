package com.yts.domain.repository

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

/**
 * Data의 Repository의 인터페이스
 * SearchRepository의 목록을 설계해놓는곳
 *
 * Data의 Repository의 실제 구현체 RepositoryImp를 가져오기 위하여 필요하다.
 */

public interface SearchRepository {
    /**
     * query = 	검색을 원하는 질의어
     * sort = 	결과 문서 정렬 방식	accuracy (정확도순) or recency (최신순)
     * page = 	결과 페이지 번호	(기본 1)	1-50 사이 Integer
     * size = 한 페이지에 보여질 문서의 개수	(기본 80)	1-80 사이 Integer
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