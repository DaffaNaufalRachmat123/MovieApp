package com.movie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.common.view.StateLayout
import com.movie.common.ResponseException
import com.movie.common.base.BaseActivity
import com.movie.common.extension.extra
import com.movie.core.Constant
import com.movie.features.main.adapter.MovieAdapter
import com.movie.features.main.adapter.MovieDiffUtil
import com.movie.features.main.databinding.ActivityMovieBinding
import com.movie.features.main.data.model.movie.Movie
import com.movie.navigation.Activities
import com.movie.navigation.startFeature
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieActivity : BaseActivity() {
    private val genreTitle by extra<String>(Constant.GENRE_TITLE)
    private val genres by extra<Int>(Constant.GENRES)
    private val movieAdapter by lazy {
        MovieAdapter(MovieDiffUtil())
    }

    private lateinit var binding : ActivityMovieBinding

    // Get Injected MainViewModel from Dagger Module class
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val mainViewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    private var stateLayout : StateLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init State Layout
        stateLayout = StateLayout(this)
            .wrap(binding.recyclerViewMovie)

        binding.apply {
            titleText.text = genreTitle

            recyclerViewMovie.apply {
                layoutManager = GridLayoutManager(this@MovieActivity , 2 , RecyclerView.VERTICAL , false)
                adapter = movieAdapter
            }

            movieAdapter.setMovieListener(object : MovieAdapter.MovieListener {
                override fun onClick(movie: Movie) {
                    startFeature(Activities.MovieActivityDetail){
                        putExtra(Constant.MOVIE_ID , movie.id.toString())
                        putExtra(Constant.MOVIE_TITLE , movie.title)
                    }
                }
            })

            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            swipeRefresh.setOnRefreshListener {
                fetchMoviesByCategory()
                swipeRefresh.isRefreshing = false
            }
        }

        fetchMoviesByCategory()
    }
    private fun fetchMoviesByCategory(){
        lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_CREATE){
                mainViewModel.getMoviesByCategory("popularity.desc" , genres.toString()).observe(this) {
                    movieAdapter.submitData(lifecycle, it)
                }
            }
        })
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                movieAdapter.loadStateFlow.collectLatest { renderMovieList(it.refresh) }
            }
        }
    }
    private fun renderMovieList(state : LoadState){
        when(state){
            is LoadState.Loading -> {
                stateLayout?.showLoading(showText = true , loadingText = resources.getString(R.string.movie_loading))
            }
            is LoadState.NotLoading -> {
                if(movieAdapter.itemCount > 0){
                    stateLayout?.showContent()
                } else {
                    stateLayout?.showEmpty(noDataText = resources.getString(R.string.movie_not_found))
                }
            }
            is LoadState.Error -> {
                if(state.error == ResponseException.Empty){
                    stateLayout?.showEmpty(noDataText = resources.getString(R.string.movie_not_found))
                } else {
                    stateLayout?.showError()
                    stateLayout?.onRetry {
                        fetchMoviesByCategory()
                    }
                }
            }
        }
    }
}