package com.movieapp.jomrun.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.jomrun.MainActivity
import com.movieapp.jomrun.databinding.FragmentMovieListBinding
import com.movieapp.jomrun.databinding.MovieSearchResultsItemBinding
import com.movieapp.jomrun.model.MovieModel
import com.movieapp.jomrun.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {
    lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, MovieListViewModel.Factory(activity.application))
            .get(MovieListViewModel::class.java)
    }
    lateinit var adapter: MovieListRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListRVAdapter(MovieItemClick {
            navigateToMovieDetailsFragment(it.imdbID)
        })
        viewModel.movieList.observe(this, Observer {
            populateData(it)
        })
    }

    private fun populateData(it: List<MovieModel>?) {
        it?.let {
            adapter.movies = it
            viewModel.showProgressBar.value = false
            viewModel.showEmptyListMsg.value = it.isEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val args = MovieListFragmentArgs.fromBundle(arguments!!)
        (activity as MainActivity).setToolbarTitle("Results for \"${args.searchKey}\"")
        (activity as MainActivity).setBackButtonEnabled(true)
        if (null != viewModel.movieList.value) {
            populateData(viewModel.movieList.value)
        } else {
            if ((activity as MainActivity).connectedToNetwork) {
                viewModel.fetchMovies(args.searchKey)
            } else {
                (activity as MainActivity).displayOfflineSnack()
            }
        }
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    private fun navigateToMovieDetailsFragment(imdbId: String) {
        if ((activity as MainActivity).connectedToNetwork) {
            binding.root.findNavController()
                .navigate(
                    MovieListFragmentDirections
                        .actionMovieListFragmentToMovieDetailsFragment(imdbId)
                )
        } else {
            (activity as MainActivity).displayOfflineSnack()
        }
    }
}

class MovieItemClick(val block: (MovieModel) -> Unit) {

    fun onClick(movieModel: MovieModel) = block(movieModel)
}

class MovieListRVAdapter(val callback: MovieItemClick) :
    RecyclerView.Adapter<MovieItemViewHolder>() {

    var movies: List<MovieModel> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.
            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val withDataBinding: MovieSearchResultsItemBinding = MovieSearchResultsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.binding.also {
            it.movieModel = movies[position]
            it.callback = callback
        }
    }
}

class MovieItemViewHolder(var binding: MovieSearchResultsItemBinding) :
    RecyclerView.ViewHolder(binding.root)
