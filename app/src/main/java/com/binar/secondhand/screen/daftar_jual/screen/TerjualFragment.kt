package com.binar.secondhand.screen.daftar_jual.screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.R
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentTerjualBinding
import com.binar.secondhand.screen.daftar_jual.TerjualViewModel
import com.binar.secondhand.screen.daftar_jual.adapter.DiminatiProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TerjualFragment : Fragment() {


    private val viewModel by viewModel<TerjualViewModel>()
    private var _binding: FragmentTerjualBinding? = null
    private val binding get() = _binding!!
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTerjualBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DiminatiProductAdapter {

        }
        binding.rvTerjual.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.rvTerjual.adapter = adapter


        with(viewModel.sellerOrder) {
            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(view.context, "Mengambil Data....", true)
            }

            onSuccess = {
                if (it.isEmpty()){
                    binding.rvTerjual.isVisible = false
                    progressDialog?.dismiss()
                    binding.imgProductNotFound.isVisible = true

                }else{
                    progressDialog?.dismiss()
                    adapter.submitList(it)
                    binding.imgProductNotFound.isVisible = false
                }
            }

            onFailure = { _, _ ->
                progressDialog?.dismiss()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
        viewModel.userChecked.onSuccess = {
            viewModel.getSellerOrder("accepted")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}