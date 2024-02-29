package com.movie.movieapp.modules

import com.movie.features.main.api.MainApi
import com.movie.features.main.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideMainRepository(mainApi : MainApi) : MainRepository =
        MainRepository(mainApi)
}