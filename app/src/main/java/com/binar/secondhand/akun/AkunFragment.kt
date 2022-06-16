package com.binar.secondhand.akun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import com.binar.secondhand.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val args = arguments?.getParcelable<Login>("login")

        val profileInteractor = ProfileUseCase.get
        profileInteractor.isUserLogon()

        binding.txtMoveToLogin.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToLoginFragment())
        }

        with(profileInteractor.userStateEventManager) {
            onLoading = {
                binding.progressBar.isVisible = true
            }

            onSuccess = {
                binding.progressBar.isVisible = false
                binding.txtName.isVisible = true
                binding.txtEmail.isVisible = true
                binding.txtName.text = it.fullName
                binding.txtEmail.text = it.email
            }

            onFailure = { _, _ ->
                Toast.makeText(requireContext(), "Kamu Belum Login", Toast.LENGTH_SHORT).show()
                binding.txtName.isVisible = false
                binding.txtEmail.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}