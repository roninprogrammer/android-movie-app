# JR Cineplex

Showcase common android implementation with [OMBD](http://www.omdbapi.com/) OMDb API. This app shows “IN THEATERS” movie list on main page, and shows clicked movie details on next page.

### Technical Features
From this project I am using,

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


![alt text](https://github.com/roninprogrammer/android-movie-app/blob/master/Untitled%20Diagram.png?raw=true)


The View :
* Activity / Fragment / View
* Requests relevant UI data from the ViewModel
* Requests the ViewModel to perform operations on the Data (insert/edit data based on user input )

The ViewModel :
* Acts as a bridge between the View and the Model
* Requests/aggregates data from the Model, and transforms it for the View
* Expose means for the View to update the Model

The Model :
* Also know as DataModel / Repository
* Holds the business logic
* Serves data from various sources (DB, REST Api)




