package com.movie.movieapp.modules

import com.movie.core.NetworkModule
import dagger.Module

/*
    Assemble all of the modules inside this class.
 */

@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        ApiModule::class,
        DataModule::class
    ]
)
open class AppModule