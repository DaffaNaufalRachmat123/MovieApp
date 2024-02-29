package com.movie.movieapp.modules

import com.movie.features.main.MovieActivity
import com.movie.features.main.MovieActivityDetail
import com.movie.features.main.MovieActivityReview
import com.movie.features.main.MovieActivityTrailer
import com.movie.features.main.MovieActivityVideo
import com.movie.features.main.MovieCategoryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/*
    Register all of the activity from another modules inside this class so the activity can use the
    DataModule , ApiModule or ViewModelModule that you have injected before.
 */

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMovieCategoryActivity() : MovieCategoryActivity
    @ContributesAndroidInjector
    abstract fun contributeMovieActivity() : MovieActivity
    @ContributesAndroidInjector
    abstract fun contributeMovieActivityDetail() : MovieActivityDetail
    @ContributesAndroidInjector
    abstract fun contributeMovieActivityTrailer() : MovieActivityTrailer
    @ContributesAndroidInjector
    abstract fun contributeMovieActivityVideo() : MovieActivityVideo
    @ContributesAndroidInjector
    abstract fun contributeMovieActivityReview() : MovieActivityReview
}