package com.movie.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.movie.common.base.BaseAdapter
import com.movie.common.base.BaseViewHolder
import com.movie.features.main.databinding.ItemCategoryBinding
import com.movie.features.main.data.model.category.Category

class CategoryAdapter : BaseAdapter<Category, ItemCategoryBinding>(){
    private var listener : CategoryListener? = null

    fun setCategoryListener(listener : CategoryListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Category, ItemCategoryBinding> {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(binding : ItemCategoryBinding) :BaseViewHolder<Category, ItemCategoryBinding>(binding){
        override fun bind(item: Category, position: Int) {
            binding.apply {
                categoryText.text = item.name
                parentContainer.setOnClickListener {
                    listener?.onClick(item)
                }
            }
        }
    }
    interface CategoryListener {
        fun onClick(category : Category)
    }
}