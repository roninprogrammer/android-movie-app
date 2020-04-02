package com.movieapp.jomrun.services


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MovieEndPoints {
    @GET("?")
    fun getMoviesListAsync(@QueryMap map: HashMap<String, String>): Deferred<NetworkMoviesContainer>

    @GET("?")
    fun getMoviesDetailsAsync(@QueryMap map: HashMap<String, String>): Deferred<NetworkMovieDetail>
}

object RetrofitObject {

    var okHttpClient = OkHttpClient.Builder()
        .build()

    var retrofit = Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(this.okHttpClient)
        .build()

    var service = retrofit.create(MovieEndPoints::class.java)
}