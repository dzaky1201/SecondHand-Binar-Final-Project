package com.binar.secondhand.screen.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.data.remote.notification.response.NotificationResponseItem
import com.binar.secondhand.core.utils.dateformat
import com.binar.secondhand.core.utils.formatRupiah
import com.binar.secondhand.databinding.ItemNotificationBinding
import com.binar.secondhand.screen.notification.NotificationFragmentDirections
import com.bumptech.glide.Glide


class ListNotifAdapter(
    private val item:List<NotificationResponseItem>
) : RecyclerView.Adapter<ListNotifAdapter.MainViewHolder>() {


    class MainViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.binding.tvProductName.text = item[position].productName
        holder.binding.tvStartPrice.text = "Harga Awal "+item[position].basePrice.toDouble().formatRupiah()
        holder.binding.tvBidPrice.text = "Ditawar "+item[position].bidPrice.toDouble().formatRupiah()
       if(item[position].updatedAt.isNotEmpty()){

           holder.binding.tvDate.text = item[position].updatedAt.dateformat()
       }else{
           holder.binding.tvDate.text ="-"
       }


        Glide.with(holder.itemView.context).load(item[position].imageUrl)
            .error(R.drawable.home_attribute)
            .centerCrop()
            .into(holder.binding.ivPosterImage)


        holder.itemView.setOnClickListener {
            it.findNavController().navigate(NotificationFragmentDirections.actionNavigationNotificationToDetailFragment(item[position].productId))
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }
}