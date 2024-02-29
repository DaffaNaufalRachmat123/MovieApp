package com.movie.features.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import com.movie.common.base.BaseActivity
import com.movie.common.extension.extra
import com.movie.core.Constant
import com.movie.features.main.databinding.ActivityMovieVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class MovieActivityVideo : BaseActivity() {
    private lateinit var binding : ActivityMovieVideoBinding

    private val videoId by extra<String>(Constant.VIDEO_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            lifecycle.addObserver(youtubePlayerView)
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId , 0f)
                }
            })
            if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}