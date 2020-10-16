# -
Daum 책검색 API을 활용한 App을 구현했으며,  
Clean Architecture와 MVVM패턴을 사용하여 구현했습니다.
# SDK Version
compileSdkVersion = 30  
targetSdkVersion = 30  
minSdkVersion = 21  
# Jetpack Libraries
- core* (최신 플랫폼 기능과 API를 지원)
- appcompat* (이전 API 버전에서 새 API에 대한 액세스를 허용 (주로 Material Design 사용))
- constraintlayout (반응형 Layout)
- recyclerview (메모리 사용량을 최소화, 많은 양의 데이터 표시)
- lifecycle (생명 주기 관리)
- navigation (UI 구조화, 딥링크, 화면간 이동에 편의를 줌)
- paging (페이지 데이터를 로드하고 RecyclerView에 표시하는 역할)
# Libraries
- material (Material Design)
- gson (Json, 객체 직렬화 역직렬화의 편의성 제공)
- glide (Image 처리)
- retrofit2 (REST API)
- rxAndroid (리액티브 프로그래밍)
- koin (의존성 주입)
# Navigation Component
![캡처](https://user-images.githubusercontent.com/23161645/94780769-eb0f6600-0403-11eb-9508-06023095434c.PNG)  
- BooksFragment (Daum 책검색 List를 뿌려주는 화면)  
- BookDeatilFragment (책 상세 화면)
# Clean Architecture + MVVM
Clean Architecture는 UI와 Database를 분리하여, 외부적인 설정에는 독립적이며 프레임워크에 의존적이지 않은 공통적인 코드를 짤 수 있다고 생각합니다.  
  
각 레이어들은 (Presentation, Data, Domain)에 의존성은 안쪽으로만 향하도록 구현하여야 합니다.  
또한 Clean Architecture은 아래와 같은 장정이 있습니다.  
- 결합성이 낮아진다.
- 구조 파악 및 유지보수가 쉽다.
- 각 레이어의 쉬운 테스트가 가능하다.

해당 앱은 Clean Architecture 및 MVVM 패턴을 사용하여 만들었습니다.  
Model + View + ViewModel로 구성된 MVVM 패턴은 Clean Architecture에서 Model은 Domain Layer 와 Data Layer부분을 의미합니다.  
  
Clean Architecture는 총 3가지의 Layer로 구분합니다.
사용자에게 보여지는 Presentation Layer  
순수한 Entity와 Data Layer와 Presentation Layer를 연결해주는 UseCase를 갖고있는 Domain Layer  
네트워크와 로컬 데이터를 가져오는 Data Layer로 구분되어 있습니다.  
## Domain 레이어
 Domain 레이어는 Android 프레임워크에 의존하지 않는 순수한 Java 혹은 Kotlin 모듈입니다.
Domain 레이어는 Entity, Repository(행동들을 담고있는), usecase(행동들의 최소 단위)를 담고있습니다.
### UseCase
```kotlin
class GetBooksUseCase(private val searchRepository: SearchRepository) {
    private val page = 1
    private val size = 50
    fun invoke(
        viewModelScope: CoroutineScope,
        token: String,
        query: String
    ): Observable<*> {
        return searchRepository.getBooks(viewModelScope, token, query, null, page, size, null)
    }
}
```
행동의 최소단위를 담고있는 UseCase입니다. 
UseCase는 전체적인 코드파악이 쉬워지며, 의존성이 낮아지기 때문에 유지보수에 용이하단 장점이 있습니다.  
위 UseCase에서는 검색이란 행동에서 책검색이란 최소단위를 처리했습니다.  
### Repository
```kotlin
interface SearchRepository {
    fun getBooks(
        viewModelScope: CoroutineScope,
        token: String,
        query: String,
        sort: String?,
        page: Int?,
        size: Int?,
        target: String?
    ): Observable<*>
}
```
검색과 관련된 행동들을 Interface로 정의한 SearchRepository입니다.  
현재는 책검색만 있지만, 다른 검색(이미지, 동영상, 웹 블로그)등이 필요하다면 여기에 작성하면 됩니다.  
여기서 Repository는 Data 레이어와 연결점이 됩니다.
### Entity
```kotlin
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
)
``` 
Entity는 프레임워크와 의존성을 가지면 안되고, 다른 프로젝트에서도 동일하게 사용할 수 있는 클래스를 작성합니다.
## Data 레이어
Data 레이어는 Domain 레어에서 설계한 Repository를 구현했으며,  
Data를 가져오기위한 Restful API, LocalCache(Room,Sharedpreferences), PagingSource(페이징 처리)을 구현합니다.   
여기서는 프레임워크에 의존성이 생기게 됩니다.  
### SearchRepositoryImp / SearchService
```kotlin
class SearchRepositoryImp(private val searchService: SearchService) : SearchRepository {
    override fun getBooks(
        viewModelScope: CoroutineScope,
        token: String,
        query: String,
        sort: String?,
        page: Int?,
        size: Int?,
        target: String?
    ): Observable<*> {
        return Pager(
            PagingConfig(
                pageSize = size ?: 50,
                enablePlaceholders = true
            )
        ) {
            BooksPagingSource(searchService, token, query, sort, size, target)
        }.observable.cachedIn(viewModelScope)

    }
}
```
```kotlin
interface SearchService {
    @GET("/v3/search/book")
    suspend fun getBooks(
        @Header("Authorization") serverAuth: String,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("target") target: String?
    ): SearchResponse
}
```
SearchRepository를 구현하는 클래스입니다.
저는 Retrofit2를 사용했고 SearchRepositoryImp에서 SearchService를 주입하여 처리하도록 구현했습니다.
## Presentation 레이어
UI레벨의 레이어 이므로 Android 프레임워크에 대한 의존성이 높습니다.  
저는 MVVM패턴의 따라 View+ViewModel의 구조로 구현 했습니다.  
View (Activity / Fragment)에선 UI와 관련된 부분을 처리하며 ViewModel에서 Domain 레이어의 알맞는 UseCase를 꺼내와 사용합니다.  
### DI (Koin)
```kotlin
var appModule = module {
    factory { (listener: OnBooksAdapterListener) -> BooksAdapter(listener) }

    viewModel {
        BooksViewModel(get(), get())
    }
    viewModel {
        BookDetailViewModel()
    }

    single { Gson() }

}
val domainModule = module {
    single<SearchRepository> { SearchRepositoryImp(get()) }
    single<GetBooksUseCase> { GetBooksUseCase(get()) }
    single<GetTokenUseCase> { GetTokenUseCase(Const.REST_API_KEY) }
}

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
```
의존성 주입 라이브러리로 Koin을 사용하여 구현 했습니다.

### ViewModel
> LifeCycle을 고려하여 UI를 관리한다. 화면회전과 같은 구성 변경이 일어나도 데이터를 보관하기 때문에 UI를 유지할수 있는 장점이 있습니다.
```kotlin
class BooksViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getBooksUseCase: GetBooksUseCase
)
```
BooksViewModel에서 다음 Restful API를 호출하기위해 필요한 Token을 가져오기위해 GetTokenUseCase와 실제적으로 책검색 리스트를 가져오기 위한 GetBooksUseCase를 주입하였습니다.
ViewModel에선 알맞은 Domain 레이어에 UseCase를 호출하면됩니다.

# 결론
위와 같은 구조로 변화의 대한 레이어가 명확해지므로 생산성과 유지보수를 증대 할 수 있다고 생각합니다.  
저는 좋은 코드의 구조가 좋은 제품으로 이어진다고 생각하기 때문에 위와 같은 아키텍쳐를 적용하고 구현했습니다.  

# PS
Data 레이어에서 Paging Source를 구현하고 전달을 해야하는데,   
Domain에선 PagingData을 전달 받을 수 없기때문에 Star-projections 로 처리하였는데,  
그렇기때문에 ViewModel에서 주입받고나서 어떤 객체인지 정확히 알수 없다.  
그래서 Viewmodel에서 전달받을때 명확하지 않은데 어떤식으로 구현하는게 옳고 좋은방법인가에 대한 의문이 남는다.

# 동작
![kakaopay_progress](https://user-images.githubusercontent.com/23161645/94793526-8e1cab80-0415-11eb-8c52-c146dbe9c1e7.gif)
