package com.yts.data.repository.page

import androidx.paging.PagingSource
import com.yts.data.source.remote.SearchService
import com.yts.domain.entity.Book

class BooksPagingSource(
    private val searchService: SearchService,
    private val token: String,
    private val query: String,
    private val sort: String?,
    private val size: Int?,
    private val target: String?
) :
    PagingSource<Int, Book>() {
    private var page = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        page++
        return try {
            var currentPage: Int? = if (params is LoadParams.Append) params.key else null

            val searchResponse = searchService.getBooks(
                token,
                query,
                sort,
                currentPage,
                size,
                target
            )

            val documents = searchResponse.documents
            val meta = searchResponse.meta

            meta?.is_end?.let { is_end ->
                if (!is_end) {
                    currentPage = (currentPage ?: 1) + 1
                }
            }

            LoadResult.Page(
                data = documents!!,
                prevKey = null,
                nextKey = currentPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}