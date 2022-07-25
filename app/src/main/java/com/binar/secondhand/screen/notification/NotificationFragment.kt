package com.binar.secondhand.screen.notification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.R
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentNotificationBinding
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.notification.adapter.ListNotifAdapter
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


        userManager.onLoading = {
            progressDialog =
                DialogWindow.progressCircle(requireContext(), "Tunggu Sebentar...", true)
        }
        userManager.onSuccess = { user ->

            binding.rvNotifList.isVisible = true
            binding.textNotification.isVisible = true

            binding.textNotification.text = "Notifikasi"

            viewModelNotif.getNotifList()
            viewModelNotif.getListNotif().observe(viewLifecycleOwner){

                if (it.isEmpty()){
                    progressDialog?.dismiss()
                    binding.rvNotifList.isVisible = false
                    binding.textNotification.isVisible = false
                    binding.emptyState.isVisible = true
                }else{
                    progressDialog?.dismiss()
                    binding.rvNotifList.isVisible = true
                    binding.textNotification.isVisible = true
                    binding.emptyState.isVisible = false
                    val listNotificationAdapter = ListNotifAdapter(it)
                    binding.rvNotifList.layoutManager =
                        LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    binding.rvNotifList.adapter = listNotificationAdapter
                }

            }
            viewModelNotif.getNotifType().observe(viewLifecycleOwner){
                viewModelNotif.getNotificationList(it)
            }


            with(viewModelNotif.notificationStateEvent) {
                onLoading = {
                    Log.d("Notification","Loading")
                }
                onSuccess = {
                    if(it.size>=1){


                        binding.rvNotifList.isVisible = true
                        binding.imgProductNotFound.isVisible = false
                        progressDialog?.dismiss()
//                        listNotificationAdapter.submitList(it)
                    }else{
                        binding.imgProductNotFound.isVisible = true

                    }

                }
                onFailure = { _, _ ->
                    Log.d("Notification","Failure")
                    progressDialog?.dismiss()
                    binding.rvNotifList.isVisible = false
                    binding.imgProductNotFound.isVisible = true
                }
            }

//            binding.buttonMenuOption.setOnClickListener {
//                dropDownOption(it)
//            }

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

    private fun dropDownOption(view: View) {
        //Popup V2
        val layoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = layoutInflater.inflate(R.layout.filter_menu_layout, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val buttonSemua: LinearLayout = popupView.findViewById(R.id.buttonSemua)
        val buttonBuyer: LinearLayout = popupView.findViewById(R.id.buttonBuyer)
        val buttonSeller: LinearLayout = popupView.findViewById(R.id.buttonSeller)

        buttonSemua.setOnClickListener {
            popupWindow.dismiss()
            viewModelNotif.notifType.value = ""
            binding.textNotification.text = "Semua"
            viewModelNotif.getNotifType().observe(viewLifecycleOwner){
                viewModelNotif.getNotificationList(it)
                Log.d("notiftype",it)
            }
        }

        buttonSeller.setOnClickListener {
            popupWindow.dismiss()
            viewModelNotif.notifType.value = "seller"
            binding.textNotification.text = "Seller"
            viewModelNotif.getNotifType().observe(viewLifecycleOwner){
                viewModelNotif.getNotificationList(it)
                Log.d("notiftype",it)
            }
        }

        buttonBuyer.setOnClickListener {
            popupWindow.dismiss()
            viewModelNotif.notifType.value = "buyer"
            binding.textNotification.text = "Buyer"
            viewModelNotif.getNotifType().observe(viewLifecycleOwner){
                viewModelNotif.getNotificationList(it)
                Log.d("notiftype",it)
            }
        }

        val values = IntArray(2)
        view.getLocationInWindow(values)
        val positionOfIcon = values[1]
        println("Position Y:$positionOfIcon")

        val displayMetrics = context?.resources?.displayMetrics
        val height = displayMetrics?.heightPixels!! * 2 / 3
        println("Height:$height")

        if (positionOfIcon > height) {
            popupWindow.showAsDropDown(view, 0, -250)
        } else {
            popupWindow.showAsDropDown(view, 0, 5)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}