package com.binar.secondhand.screen.order_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
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

        listNotificationAdapter.deleteOrder = { id ->
            progressDialog =
                DialogWindow.progressCircle(requireContext(), "Menghapus Product...", true)
            deleteOrder(id)
        }

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
                if (it.isEmpty()){
                    progressDialog?.dismiss()
                    binding.imgProductNotFound.isVisible = true

                }else{
                    progressDialog?.dismiss()
                    listNotificationAdapter.submitList(it)
                    binding.imgProductNotFound.isVisible = false
                }
            }
            onFailure = {_,_->
                progressDialog?.dismiss()
            }
        }

        viewModelDetail.deleteSuccess.observe(viewLifecycleOwner){
            progressDialog?.dismiss()
            binding.rvHistoryOrder.isVisible = false
            viewModelDetail.checkOrdersProduct()
        }

    }
    private fun deleteOrder(id: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete Order")
        dialog.setMessage("Apakah Anda Yakin Menghapusnya ?")
        dialog.setPositiveButton("Yakin") { _, _ ->
            viewModelDetail.deleteOrder(id)
        }

        dialog.setNegativeButton("Batal") { listener, _ ->
            progressDialog?.dismiss()
            listener.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}