package com.binar.secondhand.screen.daftar_jual.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentDiminatiBinding
import com.binar.secondhand.screen.daftar_jual.DiminatiViewModel
import com.binar.secondhand.screen.daftar_jual.adapter.DiminatiProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiminatiFragment : Fragment() {

    private var _binding: FragmentDiminatiBinding? = null
    private val binding get() = _binding!!
    private var progressDialog: AlertDialog? = null
    private val viewModel: DiminatiViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiminatiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DiminatiProductAdapter()
        binding.rvDiminati.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.rvDiminati.adapter = adapter


        with(viewModel.sellerOrder) {
            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(view.context, "Mengambil Data....", true)
            }

            onSuccess = {
                progressDialog?.dismiss()
                adapter.submitList(it)
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
            viewModel.getSellerOrder()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}