package com.yts.ytscleanarchitecture.di.module

import com.google.gson.Gson
import com.yts.data.repository.SearchRepositoryImp
import com.yts.data.source.remote.SearchService
import com.yts.domain.repository.SearchRepository
import com.yts.domain.usecase.search.GetBooksUseCase
import com.yts.domain.usecase.search.GetTokenUseCase
import com.yts.ytscleanarchitecture.utils.Const
import io.reactivex.schedulers.Schedulers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * DI Koin 사용....
 * 의존성 주입 라이브러리
 * 재사용성 증가 테스트 가능한 코드 보일러플레이트코드 제거
 */

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImp(get()) }
    single<GetBooksUseCase> { GetBooksUseCase(get()) }
    single<GetTokenUseCase> { GetTokenUseCase(Const.REST_API_KEY) }
}

var adapterModule = module {
}

var netModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .build()
    }

    single<SearchService> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(get())
            .build().create(SearchService::class.java)
    }
}


var viewModelModule = module {
}

var extModule = module {
    single { Gson() }
}

var moduleList = listOf(
    repositoryModule, adapterModule, netModule, viewModelModule, extModule
)