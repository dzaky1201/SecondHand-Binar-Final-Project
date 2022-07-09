package com.binar.secondhand.screen.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.core.data.local.DataPreferences
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentNotificationBinding
import com.binar.secondhand.screen.akun.AkunFragmentDirections
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.notification.adapter.ListNotificationAdapter
import com.binar.secondhand.screen.update_akun.UpdateAkunActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModelAkun: AkunViewModel by viewModel()
    private val viewModelNotif: NotificationViewModel by viewModel()
    private var progressDialog: AlertDialog? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelAkun.getUser()
        val userManager = viewModelAkun.userManager
        val prefrences = DataPreferences.get
        val token = prefrences.token
        Log.d("token", token)

        userManager.onLoading = {
            progressDialog = DialogWindow.progressCircle(requireContext(), "Tunggu Sebentar...", true)
        }
        userManager.onSuccess = { user ->
            progressDialog?.dismiss()
            binding.rvNotifList.isVisible = true
            binding.textNotification.isVisible = true
            viewModelNotif.getNotificationList()

            with(viewModelNotif.notificationStateEvent){
                onLoading = {
                    progressDialog = DialogWindow.progressCircle(requireContext(), "Mendapatkan Notifikasi...", true)
                }
                onSuccess = {
                    progressDialog?.dismiss()
                    val listNotificationAdapter = ListNotificationAdapter()
                    binding.rvNotifList.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    binding.rvNotifList.adapter = listNotificationAdapter
                    listNotificationAdapter.submitList(it)
                }
                onFailure = {_,_ ->
                    progressDialog?.dismiss()
                }
            }


        }

        userManager.onFailure = { _, _ ->
            progressDialog?.dismiss()
            binding.txtTryLogin.isVisible = true
            binding.rvNotifList.isVisible = false
            binding.textNotification.isVisible = false
            with(binding.btnTryLogin){
                isVisible = true
                setOnClickListener {
                    findNavController().navigate(NotificationFragmentDirections.actionNavigationNotificationToLoginFragment())
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}