package com.binar.secondhand.screen.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentWishlistBinding
import com.binar.secondhand.screen.notification.adapter.ListOrderHistoryAdapter
import com.binar.secondhand.screen.detailbuyer.DetailViewModel
import com.binar.secondhand.screen.wishlist.adapter.ListWishlistAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private val viewModelDetail: WishlistViewModel by viewModel()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listNotificationAdapter = ListWishlistAdapter()
        binding.rvWishlist.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvWishlist.adapter = listNotificationAdapter
        viewModelDetail.getListWishlist()
        with(viewModelDetail.listWishlistStateEvent) {

            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(
                        requireContext(),
                        "Mengambil List Favorite...",
                        true
                    )
            }
            onSuccess = {
                progressDialog?.dismiss()

                if(it.size >=1){
                    listNotificationAdapter.submitList(it)
                }else{
                    binding.textWishlist.visibility = View.INVISIBLE
                    binding.emptyState.visibility = View.VISIBLE
                }
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