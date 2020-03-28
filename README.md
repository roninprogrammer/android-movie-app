# JR Cineplex

Showcase common android implementation with [OMBD](http://www.omdbapi.com/) OMDb API. This app shows “IN THEATERS” movie list on main page, and shows clicked movie details on next page.

### Technical Features
From this project you should find useful examples like,

* Build android with MVVM design pattern + [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html) + [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
* Use Dependency Injection ([Dagger2](http://google.github.io/dagger/)) to separate configuration (properties, imageClient, httpClient, etc.) and UI usage
* Use [Butterknife](https://github.com/JakeWharton/butterknife) for view injection in Activity/Fragment
* Use [Retrofit2](http://square.github.io/retrofit/) and [Moshi](https://github.com/square/moshi) to call RESTful API and parse returned JSON response
* [RxJAVA](https://github.com/ReactiveX/RxJava) as Retrofit2 call adapter
* [Glide](https://github.com/bumptech/glide) as image client
* Define productFlavors in gradle script
* Read properties from /res/raw resource (However, BuildConfig is preferable)
* <s>How to save/restore Fragment's state</s> -> No need thanks to ViewModel
* Use RecyclerView and CardView


#### Run "check" task from Gradle

In this project, run check to ensure code quality in the following order: Checkstyle -> Findbugs -> Lint -> PMD -> Unit Tests.  You should find all configuration files under config/quality/..

```
./gradlew check
```

### References
- [Introduction to MVP on Android](https://github.com/konmik/konmik.github.io/wiki/Introduction-to-Model-View-Presenter-on-Android)
- [Architecting Android… The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
- [Dependency Injection with Dagger 2](https://guides.codepath.com/android/Dependency-Injection-with-Dagger-2)

### Dagger2 gotcha
* [Keeping the Daggers Sharp](https://medium.com/square-corner-blog/keeping-the-daggers-sharp-%EF%B8%8F-230b3191c3f)
* [Dagger 2 on production — reducing methods count](https://medium.com/azimolabs/dagger-2-on-production-reducing-methods-count-5a13ff671e30)
* [Dagger 2. Custom scopes, Subcomponents](https://proandroiddev.com/dagger-2-part-ii-custom-scopes-component-dependencies-subcomponents-697c1fa1cfc)
