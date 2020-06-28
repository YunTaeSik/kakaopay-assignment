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
 *
 * RX Android 효율적이고 신속하게 비동기 처리를 도와줌
 * 옴저버패턴(데이터 변경이 일어날 경우 객체에 의존하지 않고 데이터 변경을 통보함)을 사용하고
 * 콜백의 지옥에서 벗어날수있다!
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