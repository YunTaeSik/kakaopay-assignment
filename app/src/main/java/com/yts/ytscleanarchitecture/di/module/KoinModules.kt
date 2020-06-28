package com.yts.ytscleanarchitecture.di.module

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
 * 재사용성을 높여줍니다.
 * 테스트에 용이합니다.
 * 코드를 단순화 시켜줍니다.
 * 종속된 코드를 줄여줍니다.
 * 결합도를 낮추면서 유연성과 확장성이 향상됩니다.
 *
 * 런타임중 실행 (Dagger2는 컴파일에서 동작)
 * single = 싱글톤 생성
 * factory = inject 할때마다 인스턴스 생성
 */

val repositoryModule = module {
    koinApplication {  }
    single<SearchRepository> { SearchRepositoryImp(get()) }
    single<SearchUseCase> { SearchUseCase(get()) }
}

var adapterModule = module {
    factory<SearchAdapter> { SearchAdapter() }
    factory<FilterAdapter> { FilterAdapter() }
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

var moduleList = listOf(
    repositoryModule, adapterModule, netModule, viewModelModule
)