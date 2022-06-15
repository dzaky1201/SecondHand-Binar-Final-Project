package com.binar.secondhand.akun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.databinding.FragmentAkunBinding

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAkunBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getParcelable<Login>("login")
        binding.txtMoveToLogin.setOnClickListener {
            findNavController().navigate(AkunFragmentDirections.actionNavigationAkunToLoginFragment())
        }

        with(binding){
            txtName.text = args?.name
            txtEmail.text = args?.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}