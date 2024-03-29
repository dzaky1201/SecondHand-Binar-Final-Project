package com.binar.secondhand.screen.notification.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.utils.dateformat
import com.binar.secondhand.core.utils.formatRupiah
import com.binar.secondhand.databinding.ItemNotificationBinding
import com.binar.secondhand.screen.home.HomeFragmentDirections
import com.binar.secondhand.screen.notification.NotificationFragmentDirections
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat


class ListNotificationAdapter : ListAdapter<Notification, ListNotificationAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNotificationBinding.bind(view)

        fun bind(item: Notification) {

            binding.apply {
                binding.tvProductName.text = item.productName
                binding.tvStartPrice.text = "Harga Awal "+item.basePrice.toDouble().formatRupiah()
                binding.tvBidPrice.text = "Ditawar "+item.bidPrice.toDouble().formatRupiah()
                binding.tvDate.text = item.product.updatedAt.dateformat()


                Glide.with(binding.root).load(item.imageUrl)
                    .error(R.drawable.home_attribute)
                    .into(binding.ivPosterImage)
                root.setOnClickListener{
                    it.findNavController().navigate(NotificationFragmentDirections.actionNavigationNotificationToDetailFragment(item.productId))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }

}