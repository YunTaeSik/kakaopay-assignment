package com.yts.domain.entity


/**
 * Entity는 순수한 Kotlin모듈입니다. 안드로이드와 의존성을 가지면 안됩니다
 * 같은 서비스라면 동일한 이름으로
 * SQLITE, Realm, Room등 SQLITE 개념이 여기 들어가면 안됩니다.
 */
data class Book(
    val title: String? = null,
    val contents: String? = null,
    val url: String? = null,
    val isbn: String? = null,
    val datetime: String? = null,
    val authors: Array<String>? = null,
    val publisher: String? = null,
    val translators: Array<String>? = null,
    val price: Int? = null,
    val sale_price: Int? = null,
    val thumbnail: String? = null,
    val status: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (title != other.title) return false
        if (contents != other.contents) return false
        if (url != other.url) return false
        if (isbn != other.isbn) return false
        if (datetime != other.datetime) return false
        if (authors != null) {
            if (other.authors == null) return false
            if (!authors.contentEquals(other.authors)) return false
        } else if (other.authors != null) return false
        if (publisher != other.publisher) return false
        if (translators != null) {
            if (other.translators == null) return false
            if (!translators.contentEquals(other.translators)) return false
        } else if (other.translators != null) return false
        if (price != other.price) return false
        if (sale_price != other.sale_price) return false
        if (thumbnail != other.thumbnail) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (contents?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (isbn?.hashCode() ?: 0)
        result = 31 * result + (datetime?.hashCode() ?: 0)
        result = 31 * result + (authors?.contentHashCode() ?: 0)
        result = 31 * result + (publisher?.hashCode() ?: 0)
        result = 31 * result + (translators?.contentHashCode() ?: 0)
        result = 31 * result + (price ?: 0)
        result = 31 * result + (sale_price ?: 0)
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }
}