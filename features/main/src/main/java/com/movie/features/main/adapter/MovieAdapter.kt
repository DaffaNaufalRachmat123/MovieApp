package com.movie.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.movie.common.base.BaseViewHolder
import com.movie.common.extension.formatDate
import com.movie.common.extension.loadImageRounded
import com.movie.core.BuildConfig
import com.movie.core.Constant
import com.movie.features.main.databinding.ItemMovieBinding
import com.movie.features.main.data.model.movie.Movie


class MovieAdapter(
    diffUtil : MovieDiffUtil
) : PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(diffUtil) {
    private var listener : MovieListener? = null
    fun setMovieListener(listener : MovieListener){
        this.listener = listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item , position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(binding : ItemMovieBinding) : BaseViewHolder<Movie, ItemMovieBinding>(binding){
        override fun bind(item: Movie, position: Int) {
            binding.apply {
                movieImage.loadImageRounded("${BuildConfig.BASE_IMAGE_URL}/${item.poster_path}")
                releaseDateText.text = item.release_date.formatDate(destFormat = Constant.DEFAULT_DATE_TIME)
                movieTitleText.text = item.title
                overviewText.text = item.overview
                parentContainer.setOnClickListener {
                    listener?.onClick(item)
                }
            }
        }
    }
    interface MovieListener {
        fun onClick(movie : Movie)
    }
}

class MovieDiffUtil : DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}