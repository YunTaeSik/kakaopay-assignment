package com.yts.ytscleanarchitecture.di.module

import com.google.gson.Gson
import com.yts.data.repository.SearchRepositoryImp
import com.yts.domain.repository.SearchRepository
import com.yts.domain.usecase.search.SearchUseCase
import com.yts.ytscleanarchitecture.presentation.ui.filter.FilterAdapter
import com.yts.ytscleanarchitecture.presentation.ui.image.ImageDetailViewModel
import com.yts.ytscleanarchitecture.presentation.ui.intro.IntroViewModel
import com.yts.ytscleanarchitecture.presentation.ui.search.SearchAdapter
import com.yts.ytscleanarchitecture.presentation.ui.search.SearchViewModel
import com.yts.ytscleanarchitecture.utils.Const
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * DI Koin 사용....
 * 의존성 주입 라이브러리
 * 재사용성 증가 테스트 가능한 코드 보일러플레이트코드 제거
 *
 * 런타임중 실행 (Dagger2는 컴파일에서 동작)
 * single = 싱글톤 생성
 * factory = inject 할때마다 인스턴스 생성
 */

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImp(get()) }
    single<SearchUseCase> { SearchUseCase(get()) }
}

var adapterModule = module {
    factory<SearchAdapter> { SearchAdapter(get()) }
    factory<FilterAdapter> { FilterAdapter(get()) }
}

var netModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .build()
    }
}

var viewModelModule = module {
    viewModel {
        IntroViewModel()
    }
    viewModel {
        ImageDetailViewModel()
    }
    viewModel {
        SearchViewModel(get())
    }
}

var extModule = module {
    single { Gson() }
}

var moduleList = listOf(
    repositoryModule, adapterModule, netModule, viewModelModule, extModule
)