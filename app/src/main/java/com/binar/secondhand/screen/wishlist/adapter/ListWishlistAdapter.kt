package com.binar.secondhand.screen.wishlist.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.utils.dateformat
import com.binar.secondhand.databinding.ItemWishlistBinding
import com.binar.secondhand.screen.wishlist.WishlistFragmentDirections
import com.bumptech.glide.Glide


class ListWishlistAdapter : ListAdapter<ListWishlist, ListWishlistAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemWishlistBinding.bind(view)

        fun bind(item: ListWishlist) {
            if (item.product.name == ""|| item.product.location == "") {
                binding.tvProductName.text = "-"
                binding.tvBasePrice.text = item.product.basePrice.toString()
                binding.tvLocation.text = "Lokasi di " + " -"
                binding.tvDate.text = item.product.updatedAt.dateformat()
            }
            else if (item.product.name == ""){
                binding.tvProductName.text = "-"
                binding.tvBasePrice.text = item.product.basePrice.toString()
                binding.tvLocation.text = "Lokasi di " + item.product.location
                binding.tvDate.text = item.product.updatedAt
            }
            else if (item.product.location == ""){
                binding.tvProductName.text = item.product.name
                binding.tvBasePrice.text = item.product.basePrice.toString()
                binding.tvLocation.text = "Lokasi di " + " -"
                binding.tvDate.text = item.product.updatedAt
            }
            else {
                binding.apply {
                    binding.tvProductName.text = item.product.name
                    binding.tvBasePrice.text = item.product.basePrice.toString()
                    binding.tvLocation.text = "Deskripsi: " + item.product.description
                    binding.tvDate.text = item.product.updatedAt
                }
            }
            Glide.with(binding.root).load(item.product.imageUrl)
                .error(R.drawable.home_attribute)
                .into(binding.ivPosterImage)
            binding.root.setOnClickListener {
                it.findNavController().navigate(WishlistFragmentDirections.actionFragmentWishlistToDetailFragment(item.productId,false,item.id))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_wishlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<ListWishlist>() {
    override fun areItemsTheSame(oldItem: ListWishlist, newItem: ListWishlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListWishlist, newItem: ListWishlist): Boolean {
        return oldItem == newItem
    }

}