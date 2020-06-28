## 업무내용
 - 코틀린 기반의 카카오스타일 네이티브 안드로이드 앱 개발
 - 코틀린 + 웹앱기반의 카카오메이커스 안드로이드 앱 개발
 - 카카오톡 웹뷰 기반의 메이커스 웹뷰 개발  
  
## 지원자격
  - Android 개발 경력 2년 이상인 분
  - Kotlin과 Java에 대한 이해도가 있으신 분
  - 서비스 개선에 대한 열의와 적극적 태도가 있으신 분
  - 새로운 도전을 즐기시는 분
  
## 우대사항 
  - Kotlin으로 실 서비스 개발해보신 분
  - 디자인 패턴의 이해도가 높으신 분
  - 최신 라이브러리의 사용 경험
  
    

## Architecture [![HitCount](http://hits.dwyl.com/YunTaeSik/image-search.svg)](http://hits.dwyl.com/YunTaeSik/image-search)
 <div>
  <img src="http://miro.medium.com/max/1258/1*a-AUcEVdyRJhIepo9JyJBw.png" hspace=8 width = 250>
 </div>
     
     
     
 **Presentation Layer**  (app module)
   
 Presentation 레이어는 의존성이 높은 UI 레벨의 레이어입니다.  
현재 저는 MVVM패턴을 사용하고있습니다.  
 - [base (각종 Base 클래스)](https://github.com/YunTaeSik/image-search/tree/master/app/src/main/java/com/yts/ytscleanarchitecture/presentation/base)
 - [bidingAdapter (Android BindingAdapter)](https://github.com/YunTaeSik/image-search/tree/master/app/src/main/java/com/yts/ytscleanarchitecture/presentation/bindingAdapter) 
 - [di (Koin)](https://github.com/YunTaeSik/image-search/tree/master/app/src/main/java/com/yts/ytscleanarchitecture/di)
 - [ui (View, ViewModel)](https://github.com/YunTaeSik/image-search/tree/master/app/src/main/java/com/yts/ytscleanarchitecture/presentation/ui)
   

 **Domain Layer**  (domain java/kotlin module)
   
  Domain Layer는 순수한 Java나 Kotlin 모듈입니다. 그 이유는 도메인 레이어에서 일어나는 이유는 실제로 사용자가 하는 일련의 행동들을 적용하는 것인데 이 역시 안드로이드에 의존할 필요가 없기 때문입니다. 
저는 Entity, Response , Repository, UseCase를 담았습니다.
  - [entity (순수한 Entity값)](https://github.com/YunTaeSik/image-search/tree/master/domain/src/main/java/com/yts/domain/entity)
  - [response (API Response 객체)](https://github.com/YunTaeSik/image-search/tree/master/domain/src/main/java/com/yts/domain/response)
  - [repository (API Interface)](https://github.com/YunTaeSik/image-search/tree/master/domain/src/main/java/com/yts/domain/repository)
   - [usecase (UseCase Presentation과 연결부분)](https://github.com/YunTaeSik/image-search/tree/master/domain/src/main/java/com/yts/domain/usecase)
   


   
 **Data Layer**  (data  android module)
   
Data Layer 에서는 Domain Layer를 알고 있으므로  Domain Layer에 정의된 Repository를 실제로 구현을 하는 것입니다.   
여기에서는 Data Source에의 의존성이 생기므로 안드로이드 의존성이 생길 수 있습니다.
  - [repository (Domain repository를 실제로 구현하는부분)](https://github.com/YunTaeSik/image-search/tree/master/data/src/main/java/com/yts/data/repository)
  - [source (Retrofit2.0 Service 구현)](https://github.com/YunTaeSik/image-search/tree/master/data/src/main/java/com/yts/data/source/remote)

  
