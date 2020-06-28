package com.yts.domain.entity

/**
 * Entity는 순수한 Kotlin모듈입니다. 안드로이드와 의존성을 가지면 안됩니다
 * 같은 서비스라면 동일한 이름으로
 * SQLITE, Realm, Room등 SQLITE 개념이 여기 들어가면 안됩니다.
 */
public class Document {
    val collection :String? = null
    val thumbnail_url :String? = null
    val image_url :String? = null
    val width :Int? = null
    val height :Int? = null
    val display_sitename :String? = null
    val doc_url :String? = null
    val datetime :String? = null
}