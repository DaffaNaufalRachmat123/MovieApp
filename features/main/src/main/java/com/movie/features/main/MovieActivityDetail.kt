package com.movie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.movie.common.view.StateLayout
import com.movie.common.Result
import com.movie.common.base.BaseActivity
import com.movie.common.extension.extra
import com.movie.common.extension.formatDate
import com.movie.common.extension.loadImageRounded
import com.movie.core.BuildConfig
import com.movie.core.Constant
import com.movie.features.main.databinding.ActivityMovieDetailBinding
import com.movie.navigation.Activities
import com.movie.navigation.startFeature
import javax.inject.Inject

class MovieActivityDetail : BaseActivity() {
    private lateinit var binding : ActivityMovieDetailBinding

    private val movieId by extra<String>(Constant.MOVIE_ID)
    private val movieTitle by extra<String>(Constant.MOVIE_TITLE)

    private var stateLayout : StateLayout? = null

    // Get Injected MainViewModel from Dagger Module class
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val mainViewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init State Layout
        stateLayout = StateLayout(this)
            .wrap(binding.constraintData)

        initObserver()

        binding.apply {
            titleText.text = movieTitle
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnSeeTrailer.setOnClickListener {
                startFeature(Activities.MovieActivityTrailer){
                    putExtra(Constant.MOVIE_ID , movieId)
                    putExtra(Constant.MOVIE_TITLE , movieTitle)
                }
            }
            btnSeeReview.setOnClickListener {
                startFeature(Activities.MovieActivityReview){
                    putExtra(Constant.MOVIE_ID , movieId)
                    putExtra(Constant.MOVIE_TITLE , movieTitle)
                }
            }
            swipeRefresh.setOnRefreshListener {
                mainViewModel.getMovieDetail(movieId)
                swipeRefresh.isRefreshing = false
            }
        }
        mainViewModel.getMovieDetail(movieId)
    }
    private fun initObserver(){
        mainViewModel.movieDetailResponse.onResult { state ->
            when (state) {
                is Result.Loading -> stateLayout?.showLoading(showText = true , loadingText = resources.getString(R.string.movie_detail_loading))
                is Result.Success -> {
                    stateLayout?.showContent()
                    binding.apply {
                        movieImage.loadImageRounded("${BuildConfig.BASE_IMAGE_URL}/${state.data.poster_path}")
                        movieTitleText.text = state.data.title
                        durationText.text = state.data.runtime.toString()
                        statusText.text = state.data.status
                        releaseDateText.text = state.data.release_date.formatDate(destFormat = Constant.DEFAULT_DATE_TIME)
                        avgText.text = state.data.vote_average.toString()
                        avgCountText.text = state.data.vote_count.toString()
                        overviewText.text = state.data.overview
                    }
                }
                is Result.Failed -> {
                    stateLayout?.showError()
                    stateLayout?.onRetry {
                        mainViewModel.getMovieDetail(movieId)
                    }
                }
            }
        }
    }
}