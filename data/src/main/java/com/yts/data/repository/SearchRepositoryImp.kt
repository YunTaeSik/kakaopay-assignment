package com.yts.data.repository

import com.yts.data.source.remote.SearchService
import com.yts.domain.repository.SearchRepository
import com.yts.domain.response.SearchResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.create

/**
 * 데이터레이어는 Domain에서 구현한 Repository를 실제 구현하는 부분이다.
 * DataSource때문에 안드로이드 의존성이 생긴다.
 *
 * 도메인과 데이터레이어를 중재시켜주는 역할
 * //TODO RXAndroid 설명
 */
class SearchRepositoryImp(private val retrofit: Retrofit) : SearchRepository {
    private val authorization = "KakaoAK ebc0afd8be627ae7946c041011b88705"

    override fun getImages(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Observable<SearchResponse> {
        return retrofit.create<SearchService>().getImages(
            authorization,
            query,
            sort,
            page,
            size
        )
    }


}