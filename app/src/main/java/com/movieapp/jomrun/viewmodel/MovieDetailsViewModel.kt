package com.movieapp.jomrun.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movieapp.jomrun.database.getDatabase
import com.movieapp.jomrun.model.MovieDetails
import com.movieapp.jomrun.services.asDomainModel
import com.movieapp.jomrun.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlinx.coroutines.launch

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(getDatabase(application))

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var movieDetails = MutableLiveData<MovieDetails>()

    fun fetchMovieDetails(imdbId: String) {
        val map = HashMap<String, String>()
        map["i"] = imdbId
        map["apikey"] = "457d975b"

        viewModelScope.launch {
            movieDetails.postValue(repository.fetchMovieDetails(map).asDomainModel())
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                return MovieDetailsViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct Viewmodel")
        }
    }
}
