package com.binar.secondhand.screen.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.databinding.ItemHomeCategoryBinding


class CategoryAdapter(private val clicked: (id: Int, page: Int) -> Unit) :
    ListAdapter<Categories, CategoryAdapter.ViewHolder>(DiffCallBackCategories()) {
    var rowIndex: Int = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemHomeCategoryBinding.bind(view)


        fun bind(data: Categories) {
            binding.apply {
                binding.tvCategory.text = data.name
                root.setOnClickListener {
                    rowIndex = adapterPosition
                    notifyDataSetChanged()
                    clicked(data.id!!, 1)
                }
                if (rowIndex == adapterPosition) {
                    binding.bgCategory.setBackgroundResource(R.drawable.shape_category_home)
                    binding.ivSearch.setImageResource(R.drawable.ic_search)
                    binding.tvCategory.setTextColor(Color.WHITE)
                } else{
                    binding.bgCategory.setBackgroundResource(R.drawable.shape_category_home_white)
                    binding.ivSearch.setImageResource(R.drawable.ic_search_black)
                    binding.tvCategory.setTextColor(Color.BLACK)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class DiffCallBackCategories : DiffUtil.ItemCallback<Categories>() {
    override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem == newItem
    }

}

