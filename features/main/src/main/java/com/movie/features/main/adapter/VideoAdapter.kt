package com.movie.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.movie.common.base.BaseAdapter
import com.movie.common.base.BaseViewHolder
import com.movie.common.extension.loadImageRounded
import com.movie.core.BuildConfig
import com.movie.features.main.databinding.ItemVideoBinding
import com.movie.features.main.data.model.video.Video

class VideoAdapter : BaseAdapter<Video, ItemVideoBinding>() {
    private var listener : VideoListener? = null

    fun setVideoListener(listener : VideoListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Video, ItemVideoBinding> {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(binding : ItemVideoBinding) : BaseViewHolder<Video, ItemVideoBinding>(binding){
        override fun bind(item: Video, position: Int) {
            binding.apply {
                videoImage.loadImageRounded("${BuildConfig.BASE_YOUTUBE_IMAGE_URL}${item.key}/maxresdefault.jpg")
                videoTitleText.text = item.name
                parentContainer.setOnClickListener {
                    listener?.onClick(item)
                }
            }
        }
    }
    interface VideoListener {
        fun onClick(video : Video)
    }
}