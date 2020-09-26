package com.yts.domain.entity

import com.sun.xml.internal.fastinfoset.util.StringArray
import java.util.*

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
    val datetime: Date? = null,
    val authors: StringArray? = null,
    val publisher: String? = null,
    val translators: StringArray? = null,
    val price: Int? = null,
    val sale_price: Int? = null,
    val thumbnail: String? = null,
    val status: String? = null,
)