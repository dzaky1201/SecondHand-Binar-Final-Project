package com.binar.secondhand.screen.order_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentOrderHistoryBinding
import com.binar.secondhand.screen.notification.adapter.ListOrderHistoryAdapter
import com.binar.secondhand.screen.detailbuyer.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderHistoryFragment : Fragment() {
    private var _binding: FragmentOrderHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModelDetail: DetailViewModel by viewModel()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listNotificationAdapter = ListOrderHistoryAdapter()
        binding.rvHistoryOrder.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvHistoryOrder.adapter = listNotificationAdapter
        viewModelDetail.checkOrdersProduct()
        with(viewModelDetail.checkOrdersProductStateEvent) {

            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(
                        requireContext(),
                        "Mengambil History Transaksi...",
                        true
                    )
            }
            onSuccess = {
                progressDialog?.dismiss()
                listNotificationAdapter.submitList(it)
            }
            onFailure = {_,_->
                progressDialog?.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}