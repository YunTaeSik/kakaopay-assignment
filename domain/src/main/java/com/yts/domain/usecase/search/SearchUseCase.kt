package com.yts.domain.usecase.search

import com.yts.domain.repository.SearchRepository
import com.yts.domain.response.SearchResponse
import com.yts.domain.usecase.base.UseCase
import io.reactivex.Observable
import sun.rmi.runtime.Log
import javax.naming.directory.SearchControls

/**
 * Presenttation (ViewModel)과 Repository의 중재자 역할
 * 이미지 검색을 가져오는 유스케이스
 * SearchRepository를 주입(의존 관계를 내부가 아닌 외부)하여 케이스별로 관리한다.
 * ex : SearchUseCase , SearchDetailUseCase ....
 *
 * ...UseCase는 한개의 단위로 통일하여 명확하게 나타내야하는데
 * ..이런경우(파람이 여려개인)는 Set으로 파라미터를 설정하고 build하는게 맞는걸까?
 */
class SearchUseCase(private val searchRepository: SearchRepository) : UseCase<SearchResponse>() {

    fun getImages(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Observable<SearchResponse> {
        return searchRepository.getImages(query, sort, page, size)
    }

    override fun buildUseCase(): Observable<SearchResponse> {
        return searchRepository.getImages("", null, null, null)
    }

}