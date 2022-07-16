package com.binar.secondhand.screen.daftar_jual.screen.diminati

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.binar.secondhand.R
import com.binar.secondhand.core.utils.DateTimeFormat
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentDiminatiDetailBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiminatiDetailFragment : Fragment() {

    private var _binding: FragmentDiminatiDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<DiminatiViewModel>()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiminatiDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getInt("idDiminati")
        Log.d("getId", args.toString())
        viewModel.getDetailSellerOrder(args!!)

        with(viewModel.sellerDetailOrder) {
            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(
                        requireContext(),
                        "Loading...",
                        true
                    )
            }

            onSuccess = {
                progressDialog?.dismiss()
                with(binding) {
                    txtName.text = it.user?.fullName
                    txtCity.text = it.user?.city
                    tvProductName.text = it.productName
                    Glide.with(root).load(it.user?.imageUrl).into(ivAkun)
                    Glide.with(root).load(it.imageProduct).into(ivPosterImage)
                    tvDitawar.text = resources.getString(R.string.fixPrice, it.price.toString())
                    tvBasePrice.text =
                        resources.getString(R.string.basePrice, it.basePrice.toString())
                    tvDate.text = it.transactionDate

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}