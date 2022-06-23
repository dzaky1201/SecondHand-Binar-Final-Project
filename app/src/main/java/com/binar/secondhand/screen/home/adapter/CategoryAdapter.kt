package com.binar.secondhand.screen.home.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.databinding.ItemHomeCategoryBinding
import com.bumptech.glide.Glide


class CategoryAdapter(
private val item: List<Categories>
) : RecyclerView.Adapter<CategoryAdapter.MainViewHolder>() {

    class MainViewHolder(val binding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var index = 0;
        holder.binding.tvCategory.text = item[position].name.toString()
        holder.binding.bgCategory.setOnClickListener {
            index = position
            notifyDataSetChanged()
        }

        if(index == position){
            holder.binding.bgCategory.setBackgroundResource(R.drawable.shape_category_home)
            holder.binding.ivSearch.setBackgroundResource(R.drawable.ic_search)
            holder.binding.tvCategory.setTextColor(Color.WHITE)
//            notifyDataSetChanged()
        }else{
            holder.binding.bgCategory.setBackgroundResource(R.drawable.shape_category_home_white)
            holder.binding.ivSearch.setBackgroundResource(R.drawable.ic_search_black)
            holder.binding.tvCategory.setTextColor(Color.BLACK)
//            notifyDataSetChanged()
        }


        holder.itemView.setOnClickListener {
            index = position
            notifyDataSetChanged()
            Log.d("TEST",index.toString())
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
//
//class CategoryAdapter : ListAdapter<Categories, CategoryAdapter.ViewHolder>(diffCallBack()) {
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val binding = ItemHomeCategoryBinding.bind(view)
//
//        fun bind(data: Categories) {
//            binding.apply {
//                binding.tvCategory.text = data.name
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val data = getItem(position)
//        holder.bind(data)
//    }
//}
//
//class diffCallBack: DiffUtil.ItemCallback<Categories>(){
//    override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
//        return oldItem == newItem
//    }
//
//}

