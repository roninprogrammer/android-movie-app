package com.movieapp.jomrun.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.movieapp.jomrun.util.NetworkConnectionLiveData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var networkLiveData = NetworkConnectionLiveData(application.applicationContext)

}
