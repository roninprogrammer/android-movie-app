package com.movieapp.jomrun.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movieapp.jomrun.database.getDatabase
import com.movieapp.jomrun.model.SearchKeyModel
import com.movieapp.jomrun.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class SearchMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(
        getDatabase(application)
    )

    private var job = Job()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    var searchKeyList: LiveData<List<SearchKeyModel>>

    val showEmptyMessage: Boolean?
        get() = searchKeyList.value?.isEmpty()

    init {
        searchKeyList = movieRepository.getSearchKeys()
    }

    fun insertKey(key: String) {
        val date = Date()
        val searchKeyModel = SearchKeyModel(key, date)
        viewModelScope.launch {
            movieRepository.insertKey(searchKeyModel)
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchMovieViewModel::class.java)) {
                return SearchMovieViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct Viewmodel")
        }
    }
}
