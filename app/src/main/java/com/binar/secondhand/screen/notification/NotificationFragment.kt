package com.binar.secondhand.screen.notification

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.R
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentNotificationBinding
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.notification.adapter.ListNotificationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val viewModelAkun: AkunViewModel by viewModel()
    private val viewModelNotif: NotificationViewModel by viewModel()
    private var progressDialog: AlertDialog? = null
    private var notificationType : String = ""

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
        setHasOptionsMenu(true)
        viewModelAkun.getUser()
        val userManager = viewModelAkun.userManager
        val data = arguments?.getBoolean("isFromPageAccount")

        (activity as AppCompatActivity).supportActionBar?.title = "Notifikasi"

        userManager.onLoading = {
            progressDialog =
                DialogWindow.progressCircle(requireContext(), "Tunggu Sebentar...", true)
        }
        userManager.onSuccess = { user ->
            progressDialog?.dismiss()
            binding.rvNotifList.isVisible = true
            binding.textNotification.isVisible = true
            binding.textNotification.text = "Semua"
            viewModelNotif.getNotificationList("")
            viewModelNotif.getNotifType().observe(viewLifecycleOwner){
                viewModelNotif.getNotificationList(it)
            }

            with(viewModelNotif.notificationStateEvent) {
                onLoading = {
                    progressDialog = DialogWindow.progressCircle(
                        requireContext(),
                        "Mendapatkan Notifikasi...",
                        true
                    )
                }
                onSuccess = {
                    progressDialog?.dismiss()
                    val listNotificationAdapter = ListNotificationAdapter()
                    binding.rvNotifList.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    binding.rvNotifList.adapter = listNotificationAdapter
                    listNotificationAdapter.submitList(it)
                }
                onFailure = { _, _ ->
                    progressDialog?.dismiss()
                }
            }

        }

        userManager.onFailure =
            { _, _ ->
                progressDialog?.dismiss()
                binding.txtTryLogin.isVisible = true
                binding.rvNotifList.isVisible = false
                binding.textNotification.isVisible = false
                with(binding.btnTryLogin) {
                    isVisible = true
                    setOnClickListener {
                        findNavController().navigate(NotificationFragmentDirections.actionNavigationNotificationToLoginFragment())
                    }
                }
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notif_menu,menu)
        return super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if(id == R.id.menuBuyer){
            viewModelNotif.notifType.value = "buyer"
            Log.d("Notifftype",notificationType)
            binding.textNotification.text = "Buyer"
        }
        if(id == R.id.menuSeller){
            viewModelNotif.notifType.value = "seller"
            Log.d("Notifftype",notificationType)
            binding.textNotification.text = "Seller"
        }
        if(id == R.id.menuAll){
            viewModelNotif.notifType.value = ""
            binding.textNotification.text = "Semua"
            Log.d("Notifftype",notificationType)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.show()

    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}