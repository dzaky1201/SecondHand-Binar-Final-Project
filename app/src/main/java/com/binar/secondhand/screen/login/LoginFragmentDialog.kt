package com.binar.secondhand.screen.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.databinding.FragmentLoginBinding
import com.binar.secondhand.databinding.LoginFragmentDialogBinding
import com.binar.secondhand.screen.jual.JualViewModel
import com.binar.secondhand.screen.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragmentDialog: DialogFragment() {
    private var _binding: LoginFragmentDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()
    private val jualViewModel by viewModel<JualViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnMasuk.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.requestLogin(email, password)
            }

            txtDaftar.setOnClickListener {
                startActivity(Intent(requireActivity(), RegisterActivity::class.java))
            }

            profileLoginProgressBar.isVisible = false
            with(viewModel.loginEventManager) {
                onLoading = {
                    profileLoginProgressBar.isVisible = true
                    btnMasuk.isEnabled = false
                }

                onSuccess = {
                    profileLoginProgressBar.isVisible = false
                    btnMasuk.isEnabled = true
                    viewModel.saveToken(it)
                    dialog?.dismiss()
                }
                onFailure = { code, ex ->
                    profileLoginProgressBar.isVisible = false
                    btnMasuk.isEnabled = true
                    if (code == 503){
                        Toast.makeText(requireContext(), "error 503", Toast.LENGTH_SHORT).show()
                    }
                    // failure
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}