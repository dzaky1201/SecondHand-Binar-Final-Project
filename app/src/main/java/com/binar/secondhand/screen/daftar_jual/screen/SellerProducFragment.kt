package com.binar.secondhand.screen.daftar_jual.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentSellerProducBinding
import com.binar.secondhand.screen.daftar_jual.SellerProducViewModel
import com.binar.secondhand.screen.daftar_jual.adapter.SellerProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerProducFragment : Fragment() {

    private var _binding: FragmentSellerProducBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SellerProducViewModel by viewModel()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerProducBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sellerAdapter = SellerProductAdapter()
        binding.rvSellerProduct.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvSellerProduct.adapter = sellerAdapter

        with(viewModel.productSellerStateEvent) {
            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(requireContext(), "Please wait...", true)
            }
            onSuccess = {
                progressDialog?.dismiss()
                sellerAdapter.submitList(it)
            }
            onFailure = { _, _ ->
                progressDialog?.dismiss()
                Toast.makeText(requireContext(), "terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
        viewModel.userChecked.onSuccess = {
            viewModel.getProductSeller()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}