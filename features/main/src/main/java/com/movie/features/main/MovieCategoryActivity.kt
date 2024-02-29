package com.movie.features.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.common.view.StateLayout
import com.movie.common.Result
import com.movie.common.base.BaseActivity
import com.movie.core.Constant
import com.movie.features.main.adapter.CategoryAdapter
import com.movie.features.main.databinding.ActivityMovieCategoryBinding
import com.movie.features.main.data.model.category.Category
import com.movie.navigation.Activities
import com.movie.navigation.startFeature
import javax.inject.Inject

class MovieCategoryActivity : BaseActivity() {
    private lateinit var binding : ActivityMovieCategoryBinding
    private var stateLayout : StateLayout? = null

    // Get Injected MainViewModel from Dagger Module class
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val mainViewModel : MainViewModel by viewModels {
        viewModelFactory
    }

    // Init Categories Adapter
    private val categoryAdapter by lazy { CategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init State Layout
        stateLayout = StateLayout(this)
            .wrap(binding.recyclerViewCategories)

        // Init Recyclerview
        binding.apply {
            recyclerViewCategories.apply {
                layoutManager = LinearLayoutManager(this@MovieCategoryActivity , RecyclerView.VERTICAL , false)
                adapter = categoryAdapter
            }
            swipeRefresh.setOnRefreshListener {
                mainViewModel.getMovieCategories()
                swipeRefresh.isRefreshing = false
            }
            categoryAdapter.setCategoryListener(object : CategoryAdapter.CategoryListener {
                override fun onClick(category: Category) {
                    startFeature(Activities.MovieActivity){
                        putExtra(Constant.GENRE_TITLE , category.name)
                        putExtra(Constant.GENRES , category.id)
                    }
                }
            })
        }

        initObserver()

        mainViewModel.getMovieCategories()
    }

    private fun initObserver(){
        mainViewModel.movieCategoriesResponse.onResult { state ->
            when(state){
                is Result.Loading -> {
                    stateLayout?.showLoading(showText = true , loadingText = resources.getString(R.string.categories_loading))
                }
                is Result.Success -> {
                    if(state.data.genres.size > 0){
                        stateLayout?.showContent()
                        categoryAdapter.submitList(state.data.genres)
                    } else {
                        stateLayout?.showEmpty(noDataText = resources.getString(R.string.categories_not_found))
                    }
                }
                is Result.Failed -> {
                    stateLayout?.showError()
                    stateLayout?.onRetry {
                        mainViewModel.getMovieCategories()
                    }
                }
            }
        }
    }
}