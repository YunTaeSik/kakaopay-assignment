package com.yts.ytscleanarchitecture.di.module

import com.yts.data.repository.SearchRepositoryImp
import com.yts.domain.repository.SearchRepository
import com.yts.domain.usecase.search.GetBooksUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.utils.Const
import org.koin.dsl.module

val domainModule = module {
    single<SearchRepository> { SearchRepositoryImp(get()) }
    single<GetBooksUseCase> { GetBooksUseCase(get()) }
    single<GetTokenUseCase> { GetTokenUseCase(Const.REST_API_KEY) }
}
