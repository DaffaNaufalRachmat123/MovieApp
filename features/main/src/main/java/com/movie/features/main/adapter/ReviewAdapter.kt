package com.movie.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.movie.common.base.BaseViewHolder
import com.movie.common.extension.loadImageRounded
import com.movie.core.BuildConfig
import com.movie.features.main.databinding.ItemReviewBinding
import com.movie.features.main.data.model.review.Review

class ReviewAdapter(
    diffUtil: ReviewDiffUtil
) : PagingDataAdapter<Review, ReviewAdapter.ViewHolder>(diffUtil) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item , position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(binding : ItemReviewBinding) : BaseViewHolder<Review, ItemReviewBinding>(binding){
        override fun bind(item: Review, position: Int) {
            binding.apply {
                if(!item.author_details.avatar_path.isNullOrEmpty())
                    avatarImage.loadImageRounded("${BuildConfig.BASE_IMAGE_URL}/${item.author_details.avatar_path}")
                else
                    avatarImage.setImageResource(com.movie.uiresources.R.drawable.ic_default_user)
                if(item.author_details.name.isNotEmpty()){
                    nameText.text = item.author_details.name
                } else {
                    nameText.text = "No name"
                }
                reviewText.text = item.content
            }
        }
    }
}

class ReviewDiffUtil : DiffUtil.ItemCallback<Review>(){
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}