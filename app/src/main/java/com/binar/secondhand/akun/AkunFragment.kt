package com.binar.secondhand.akun

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.core.data.source.local.DataPreferences
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AkunViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        val userManager = viewModel.userManager
        val prefrences = DataPreferences.get
        val token = prefrences.token
        Log.d("token", token)

        userManager.onLoading = {
            binding.progressBar.isVisible = true
        }

        userManager.onSuccess = {
            binding.progressBar.isVisible = false
            binding.includeAkunSaya.layoutAkun.isVisible = true
            Glide.with(view).load(it.imageUrl).into(binding.includeAkunSaya.imgProfile)
        }

        userManager.onFailure = { _, _ ->
            binding.progressBar.isVisible = false
            Toast.makeText(requireContext(), "Kamu Belum Login", Toast.LENGTH_SHORT).show()
            binding.txtTryLogin.isVisible = true
            with(binding.btnTryLogin){
                isVisible = true
                setOnClickListener {
                    findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToLoginFragment())
                }
            }
            binding.includeAkunSaya.layoutAkun.isVisible = false
        }

        with(binding.includeAkunSaya){
            btnLogout.setOnClickListener {
                val dialog = AlertDialog.Builder(view.context)
                dialog.setTitle("Logout")
                dialog.setMessage("Apakah Anda Yakin Ingin Logout ?")
                dialog.setPositiveButton("Yakin") { _, _ ->
                    viewModel.clearSession()
                    findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToLoginFragment())
                }

                dialog.setNegativeButton("Batal") { listener, _ ->
                    listener.dismiss()
                }

                dialog.show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}