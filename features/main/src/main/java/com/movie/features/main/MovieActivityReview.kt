package com.movie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.common.view.StateLayout
import com.movie.common.ResponseException
import com.movie.common.base.BaseActivity
import com.movie.common.extension.extra
import com.movie.core.Constant
import com.movie.features.main.adapter.ReviewAdapter
import com.movie.features.main.adapter.ReviewDiffUtil
import com.movie.features.main.databinding.ActivityMovieReviewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieActivityReview : BaseActivity() {
    private lateinit var binding : ActivityMovieReviewBinding

    private val movieId by extra<String>(Constant.MOVIE_ID)
    private val movieTitle by extra<String>(Constant.MOVIE_TITLE)
    private val reviewAdapter by lazy { ReviewAdapter(ReviewDiffUtil()) }

    private var stateLayout : StateLayout? = null

    // Get Injected MainViewModel from Dagger Module class
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val mainViewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init State Layout
        stateLayout = StateLayout(this)
            .wrap(binding.recyclerViewReview)

        binding.apply {
            titleText.text = resources.getString(R.string.review_title , movieTitle)
            recyclerViewReview.apply {
                layoutManager = LinearLayoutManager(this@MovieActivityReview , RecyclerView.VERTICAL , false)
                adapter = reviewAdapter
            }
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            swipeRefresh.setOnRefreshListener {
                fetchReviews()
                swipeRefresh.isRefreshing = false
            }
        }
        fetchReviews()
    }
    private fun fetchReviews(){
        lifecycle.addObserver(LifecycleEventObserver { _ , event ->
            if(event == Lifecycle.Event.ON_CREATE){
                mainViewModel.getReviews(movieId).observe(this){
                    reviewAdapter.submitData(lifecycle , it)
                }
            }
        })
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                reviewAdapter.loadStateFlow.collectLatest { renderReviewList(it.refresh) }
            }
        }
    }
    private fun renderReviewList(state : LoadState){
        when(state){
            is LoadState.Loading -> {
                stateLayout?.showLoading(showText = true , loadingText = resources.getString(R.string.review_loading))
            }
            is LoadState.NotLoading -> {
                if(reviewAdapter.itemCount > 0){
                    stateLayout?.showContent()
                } else {
                    stateLayout?.showEmpty(noDataText = resources.getString(R.string.review_not_found))
                }
            }
            is LoadState.Error -> {
                if(state.error == ResponseException.Empty){
                    stateLayout?.showEmpty(noDataText = resources.getString(R.string.review_not_found))
                } else {
                    stateLayout?.showError()
                    stateLayout?.onRetry {
                        fetchReviews()
                    }
                }
            }
        }
    }
}