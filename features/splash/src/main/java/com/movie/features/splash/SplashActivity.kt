package com.movie.features.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.movie.navigation.Activities
import com.movie.navigation.startFeature

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
               run {
                   startFeature(Activities.MovieCategoryActivity){}
                   finish()
               }
        } , 1500)
    }
}