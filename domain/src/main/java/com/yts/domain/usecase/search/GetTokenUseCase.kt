package com.yts.domain.usecase.search

class GetTokenUseCase(private val restApiKey: String) {
    fun invoke(): String {
        return "KakaoAK $restApiKey"
    }

}