package com.binar.secondhand.screen.jual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.databinding.FragmentJualBinding
import com.binar.secondhand.databinding.ItemHomeCategoryBinding
import com.binar.secondhand.databinding.LoginFragmentDialogBinding
import com.binar.secondhand.screen.jual.add_product.AddProductActivity
import com.binar.secondhand.screen.login.LoginFragmentDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<JualViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("CekLifecycle", "onCreateView")
        _binding = FragmentJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        with(viewModel.usertStateEventManager) {
            onLoading = {
                binding.progressBar.isVisible = true
            }

            onSuccess = {
                binding.progressBar.isVisible = false
                binding.includeJual.layoutAddProduct.isVisible = true
            }

            onFailure = { _, _ ->
                binding.progressBar.isVisible = false
                binding.txtTryLogin.isVisible = true
                with(binding.btnTryLogin) {
                    isVisible = true
                    setOnClickListener {
                        findNavController().navigate(JualFragmentDirections.actionNavigationJualToLoginFragment())
                    }
                }
                binding.includeJual.layoutAddProduct.isVisible = false
            }
        }

        binding.includeJual.btnAddProduct.setOnClickListener {
            startActivity(Intent(requireActivity(), AddProductActivity::class.java))
        }

//        binding.btnTryLogin.setOnClickListener {
//            val add = LoginFragmentDialog()
//            add.show(childFragmentManager, "login")
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}