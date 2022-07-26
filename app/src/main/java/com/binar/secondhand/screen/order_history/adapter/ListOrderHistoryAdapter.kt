package com.binar.secondhand.screen.notification.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.utils.dateformat
import com.binar.secondhand.core.utils.formatRupiah
import com.binar.secondhand.databinding.ItemOrderHistoryBinding
import com.bumptech.glide.Glide
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ListOrderHistoryAdapter : ListAdapter<OrdersProduct, ListOrderHistoryAdapter.ViewHolder>(
    DiffCallBackHistory()
) {
    var deleteOrder: ((id: Int) -> Unit)? = null
    var refreshList: (() -> Unit)?=  null
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemOrderHistoryBinding.bind(view)

        fun bind(item: OrdersProduct) {

            binding.apply {
                val str = item.transactionDate
                binding.tvProductName.text = item.productName
                binding.tvBasePrice.text ="Harga Awal "+item.basePrice.toDouble().formatRupiah()
                binding.tvSellerName.text = "Status Barang : "+item.status
                    binding.tvDate.text = str.dateformat()



                Glide.with(binding.root).load(item.product.imageUrl)
                    .error(R.drawable.home_attribute)
                    .centerCrop()
                    .into(binding.ivPosterImage)

                binding.imgDelete.setOnClickListener {
                    deleteOrder?.invoke(item.id)
                    refreshList?.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }
}

class DiffCallBackHistory : DiffUtil.ItemCallback<OrdersProduct>() {
    override fun areItemsTheSame(oldItem: OrdersProduct, newItem: OrdersProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrdersProduct, newItem: OrdersProduct): Boolean {
        return oldItem == newItem
    }

}