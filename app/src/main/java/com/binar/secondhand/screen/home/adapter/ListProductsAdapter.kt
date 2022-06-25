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
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.databinding.ItemHomeListProductsBinding
import com.bumptech.glide.Glide

class ListProductsAdapter(
    private val item: List<Product>
) : RecyclerView.Adapter<ListProductsAdapter.MainViewHolder>() {

    class MainViewHolder(val binding: ItemHomeListProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemHomeListProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.tvTitle.text = item[position].name
        holder.binding.tvPrice.text = item[position].basePrice.toString()
        if(item[position].categories.isNotEmpty()){
            holder.binding.tvCategory.text = item[position].categories[0].name
        }
        Glide.with(holder.binding.root).load(item[position].imageUrl)
            .error(R.drawable.home_attribute)
            .into(holder.binding.ivPosterImage)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}

//class ListProductsAdapter : ListAdapter<Product, ListProductsAdapter.ViewHolder>(DiffCallBack()) {
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val binding = ItemHomeListProductsBinding.bind(view)
//
//        fun bind(item: Product) {
//
//            binding.apply {
//                binding.tvTitle.text = item.name
//                binding.tvPrice.text = item.basePrice.toString()
//                binding.tvCategory.text = item.Product[0].name
//
//
//                Glide.with(binding.root).load(item.imageUrl)
//                    .error(R.drawable.home_attribute)
//                    .into(binding.ivPosterImage)
////                root.setOnClickListener {
////                 val id = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.id ?: 0)
////                  it.findNavController().navigate(id)
////                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_home_list_products, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//}
//
// class DiffCallBack: DiffUtil.ItemCallback<Product>(){
//    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
//        return oldItem == newItem
//    }
//
//}