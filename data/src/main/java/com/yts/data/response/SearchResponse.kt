package com.yts.data.response

import com.yts.domain.entity.Book
import com.yts.domain.entity.Meta


data class SearchResponse(
    val meta: Meta? = null,
    val documents: List<Book>? = null
)