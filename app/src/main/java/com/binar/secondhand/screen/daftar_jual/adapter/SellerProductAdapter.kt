package com.binar.secondhand.screen.daftar_jual.adapter


import android.text.SpannableString
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


class SellerProductAdapter : ListAdapter<Product, SellerProductAdapter.ViewHolder>(DiffCallBack()) {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemHomeListProductsBinding.bind(view)

        fun bind(item: Product) {

            binding.apply {
                binding.tvTitle.text = item.name
                binding.tvPrice.text = item.basePrice.toString()
                if (item.categories.isNotEmpty()) {
                    item.categories.forEach {
                        val span = SpannableString(it.name + "\n")
                        binding.tvCategory.text = span
                    }
                }

                Glide.with(binding.root).load(item.imageUrl)
                    .error(R.drawable.home_attribute)
                    .into(binding.ivPosterImage)
                    
//                root.setOnClickListener{
//                    it.findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment(item.id))
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_list_products, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}