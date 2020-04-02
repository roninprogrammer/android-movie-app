package com.movieapp.jomrun.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.movieapp.jomrun.MainActivity
import com.movieapp.jomrun.databinding.FragmentMovieDetailsBinding
import com.movieapp.jomrun.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, MovieDetailsViewModel.Factory(activity.application))
            .get(MovieDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val args = MovieDetailsFragmentArgs.fromBundle(arguments!!)
        val imdbID = args.imdbId
        binding.lifecycleOwner = this

        if ((activity as MainActivity).connectedToNetwork) {
            viewModel.fetchMovieDetails(imdbID)
        } else {
            (activity as MainActivity).displayOfflineSnack()
        }
        (activity as MainActivity).setBackButtonEnabled(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movieDetails.observe(this, Observer {
            binding.movie = it
            binding.progressCircular?.visibility = View.GONE
            binding.svMain?.visibility = View.VISIBLE
            (activity as MainActivity).setToolbarTitle(it.Title)
        })
    }
}