package com.movieapp.jomrun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.movieapp.jomrun.databinding.ActivityMainBinding
import com.movieapp.jomrun.util.showSnackBar
import com.movieapp.jomrun.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: SharedViewModel
    lateinit var binding: ActivityMainBinding
    var connectedToNetwork = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        viewModel.networkLiveData.observe(this, Observer {
            if (!it) {
                displayOfflineSnack()
            } else {
                if (!connectedToNetwork)
                    binding.clMain.showSnackBar("Internet is back. You can resume using app now.")
            }
            connectedToNetwork = it
        })
    }

    fun displayOfflineSnack() {
        binding.clMain.showSnackBar("You are offline. Please connect to the internet")
    }

    fun setToolbarTitle(myTitle: String) {
        supportActionBar?.title = myTitle
    }

    fun setBackButtonEnabled(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
