package com.movie.movieapp.modules

import com.movie.features.main.api.MainApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit) : MainApi =
        retrofit.create(MainApi::class.java)
}