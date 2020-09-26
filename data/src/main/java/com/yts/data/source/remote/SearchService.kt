package com.yts.data.source.remote

import com.yts.data.response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * 레트로핏2를 사용하였음
 * Restful api 모든 자원을 URI
 * Post생성 get조회 put수정 delete삭제
 * 401 인증실패 404페이지를 찾을수없음 500서버에서 문제 발생
 */
interface SearchService {
    @GET("/v3/search/book ")
    fun getBooks(
        @Header("Authorization") serverAuth: String,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("target") target: String?
    ): SearchResponse
}