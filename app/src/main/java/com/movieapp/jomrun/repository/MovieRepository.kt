package com.movieapp.jomrun.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.movieapp.jomrun.database.MovieDatabase
import com.movieapp.jomrun.database.asDomainModel
import com.movieapp.jomrun.model.SearchKeyModel
import com.movieapp.jomrun.services.NetworkMovieDetail
import com.movieapp.jomrun.services.NetworkMoviesContainer
import com.movieapp.jomrun.services.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MovieDatabase) {

    fun getSearchKeys() : LiveData<List<SearchKeyModel>> {
        return Transformations.map(database.movieSearchKeyDao.getAllSearchKeys()) {
            it.asDomainModel()
        }
    }

    suspend fun insertKey(searchKeyModel: SearchKeyModel) {
        withContext(Dispatchers.IO) {
            database.movieSearchKeyDao.insertKey(searchKeyModel.asDatabaseModel())
        }
    }

    suspend fun fetchMovies(queryMap: HashMap<String, String>): NetworkMoviesContainer {
        return RetrofitObject.service.getMoviesListAsync(queryMap).await()
    }

    suspend fun fetchMovieDetails(queryMap: HashMap<String, String>): NetworkMovieDetail {
        return RetrofitObject.service.getMoviesDetailsAsync(queryMap).await()
    }
}