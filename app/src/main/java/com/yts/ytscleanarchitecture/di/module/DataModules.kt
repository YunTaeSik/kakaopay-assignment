package com.yts.ytscleanarchitecture.di.module

import com.yts.data.source.remote.SearchService
import com.yts.ytscleanarchitecture.utils.Const
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var dataModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single<SearchService> {
        get<Retrofit>().create(SearchService::class.java)
    }
}
