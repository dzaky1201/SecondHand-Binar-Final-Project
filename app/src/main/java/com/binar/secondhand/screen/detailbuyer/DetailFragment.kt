package com.binar.secondhand.screen.detailbuyer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentDetailBuyerBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBuyerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBuyerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getInt("idProduct")
        viewModel.getDetailProduct(data ?: 0)

        with(viewModel.detailStateEvent){
            onLoading = {
                Log.d("Detail API","Loading Detail")
            }
            onSuccess = {
               binding.also { bin ->
                   Glide.with(view).load(it.image_url).into(bin.ivProduct)
                   Glide.with(view).load(it.user.imageUrl).into(bin.ivSellerProfile)
                   bin.tvName.text = it.name
//                   bin.tvCategory.text = it.Categories[0].name
                   bin.tvDescription.text = it.description
                   bin.tvNameSeller.text = it.user.fullname
                   bin.tvCity.text = it.user.city

               }
               }

            onFailure = {  _, _ ->
                Log.d("Detail API","Loading Detail")
            }
            }
        }
    }