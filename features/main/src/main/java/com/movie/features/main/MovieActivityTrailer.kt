package com.movie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.common.view.StateLayout
import com.movie.common.Result
import com.movie.common.base.BaseActivity
import com.movie.common.extension.extra
import com.movie.core.Constant
import com.movie.features.main.adapter.VideoAdapter
import com.movie.features.main.data.model.video.Video
import com.movie.features.main.databinding.ActivityMovieTrailerBinding
import com.movie.navigation.Activities
import com.movie.navigation.startFeature
import javax.inject.Inject

class MovieActivityTrailer : BaseActivity() {
    private lateinit var binding : ActivityMovieTrailerBinding

    private val movieId by extra<String>(Constant.MOVIE_ID)
    private val movieTitle by extra<String>(Constant.MOVIE_TITLE)
    private val videoAdapter by lazy {
        VideoAdapter()
    }

    private var stateLayout : StateLayout? = null

    // Get Injected MainViewModel from Dagger Module class
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val mainViewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieTrailerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stateLayout = StateLayout(this)
            .wrap(binding.recyclerViewMovieTrailer)

        binding.apply {
            titleText.text = resources.getString(R.string.trailer_video_title , movieTitle)
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            recyclerViewMovieTrailer.apply {
                layoutManager = LinearLayoutManager(this@MovieActivityTrailer , RecyclerView.VERTICAL , false)
                adapter = videoAdapter
            }
        }
        videoAdapter.setVideoListener(object : VideoAdapter.VideoListener {
            override fun onClick(video: Video) {
                startFeature(Activities.MovieActivityVideo){
                    putExtra(Constant.VIDEO_ID , video.key)
                }
            }
        })

        initObserver()

        mainViewModel.getTrailerVideos(movieId)
    }
    private fun initObserver(){
        mainViewModel.trailerVideoResponse.onResult { state ->
            when (state) {
                is Result.Loading -> stateLayout?.showLoading(showText = true , loadingText = resources.getString(R.string.trailer_video_loading))
                is Result.Success -> {
                    if(state.data.results.size > 0){
                        stateLayout?.showContent()
                        videoAdapter.submitList(state.data.results)
                    } else {
                        stateLayout?.showEmpty(noDataText = resources.getString(R.string.trailer_video_not_found))
                    }
                }
                is Result.Failed -> {
                    stateLayout?.showError()
                    stateLayout?.onRetry {
                        mainViewModel.getTrailerVideos(movieId)
                    }
                }
            }
        }
    }
}