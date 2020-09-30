package com.yts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.yts.data.repository.page.BooksPagingSource
import com.yts.data.source.remote.SearchService
import com.yts.domain.entity.Book
import com.yts.domain.repository.SearchRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

/**
 * 데이터레이어는 Domain에서 구현한 Repository를 실제 구현하는 부분이다.
 * DataSource때문에 안드로이드 의존성이 생긴다.
 *
 * 도메인과 데이터레이어를 중재시켜주는 역할
 *
 * RX Android 효율적이고 신속하게 비동기 처리를 도와줌
 * 옴저버패턴(데이터 변경이 일어날 경우 객체에 의존하지 않고 데이터 변경을 통보함)을 사용하고
 * 콜백의 지옥에서 벗어날수있다!
 */
class SearchRepositoryImp(private val searchService: SearchService) : SearchRepository {
    override fun getBooks(
        viewModelScope: CoroutineScope,
        token: String,
        query: String,
        sort: String?,
        page: Int?,
        size: Int?,
        target: String?
    ): Observable<*> {
        return Pager(
            PagingConfig(
                pageSize = size ?: 50,
                enablePlaceholders = true
            )
        ) {
            BooksPagingSource(searchService, token, query, sort, size, target)
        }.observable.cachedIn(viewModelScope)

    }
}