package com.yts.data.repository.page

import android.util.Log
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
    private var currentPage = 1


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        currentPage = if (params is LoadParams.Append) params.key else 1
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
        currentPage++

        return try {
            LoadResult.Page(
                data = documents!!,
                prevKey = null,
                nextKey = if (meta?.is_end == true) null else currentPage
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}