package com.binar.secondhand.screen.akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.binar.secondhand.screen.update_akun.UpdateAkunActivity
import com.bumptech.glide.Glide
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

        userManager.onLoading = {
            binding.progressBar.isVisible = true
        }

        userManager.onSuccess = { user ->
            binding.progressBar.isVisible = false
            binding.includeAkunSaya.layoutAkun.isVisible = true
            Glide.with(binding.root).load(user.imageUrl).centerCrop().error(R.drawable.ic_baseline_person_).into(binding.includeAkunSaya.imgProfile)
            binding.includeAkunSaya.btnUbahAkun.setOnClickListener {
                val intent = Intent(requireActivity(), UpdateAkunActivity::class.java)
                intent.putExtra("showData", user)
                startActivity(intent)
            }
        }

        userManager.onFailure = { _, _ ->
            binding.progressBar.isVisible = false
            binding.txtTryLogin.isVisible = true
            with(binding.btnTryLogin) {
                isVisible = true
                setOnClickListener {
                    findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToLoginFragment())
                }
            }
            binding.includeAkunSaya.layoutAkun.isVisible = false
        }

        with(binding.includeAkunSaya) {
            btnLogout.setOnClickListener {
                val dialog = AlertDialog.Builder(view.context)
                dialog.setTitle("Logout")
                dialog.setMessage("Apakah Anda Yakin Ingin Logout ?")
                dialog.setPositiveButton("Yakin") { _, _ ->
                    viewModel.clearSession()
                    viewModel.getUser()
                    binding.includeAkunSaya.layoutAkun.isVisible = false
                }

                dialog.setNegativeButton("Batal") { listener, _ ->
                    listener.dismiss()
                }

                dialog.show()
            }
        }

        binding.includeAkunSaya.btnOrderHistory.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToOrderHistoryFragment())
        }
        binding.includeAkunSaya.btnWishList.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToFragmentWishlist())
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}