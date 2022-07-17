package com.binar.secondhand.screen.daftar_jual.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.databinding.ItemNotificationBinding
import com.bumptech.glide.Glide

class DiminatiProductAdapter(private val onClick: (Int) -> (Unit)) :
    ListAdapter<SellerProductInterestedEntity, DiminatiProductAdapter.ViewHolder>(
        DiffCallBackDiminati()
    ) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNotificationBinding.bind(view)

        fun bind(item: SellerProductInterestedEntity) {

            binding.apply {
                binding.tvProductName.text = item.productName
                if (item.status == "accepted") {
                    binding.tvStartPrice.apply {
                        text = itemView.resources.getString(R.string.basePrice, item.basePrice)
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                } else {
                    binding.tvStartPrice.text =
                        itemView.resources.getString(R.string.basePrice, item.basePrice)

                }
                binding.tvStartPrice.text =
                    itemView.resources.getString(R.string.basePrice, item.basePrice)
                binding.tvBidPrice.text =
                    itemView.resources.getString(R.string.fixPrice, item.price.toString())
                binding.tvDate.text = item.transactionDate


                Glide.with(binding.root).load(item.imageProduct)
                    .error(R.drawable.home_attribute)
                    .into(binding.ivPosterImage)
                root.setOnClickListener {
                    onClick(item.id!!)
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

class DiffCallBackDiminati : DiffUtil.ItemCallback<SellerProductInterestedEntity>() {
    override fun areItemsTheSame(
        oldItem: SellerProductInterestedEntity,
        newItem: SellerProductInterestedEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SellerProductInterestedEntity,
        newItem: SellerProductInterestedEntity
    ): Boolean {
        return oldItem == newItem
    }

}