package com.binar.secondhand.screen.daftar_jual.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentDaftarJualBinding
import com.binar.secondhand.screen.daftar_jual.DaftarJualViewModel
import com.binar.secondhand.screen.daftar_jual.adapter.SectionPagerAdapter
import com.binar.secondhand.screen.update_akun.UpdateAkunActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DaftarJualFragment : Fragment() {
    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<DaftarJualViewModel>()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarJualBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()

        with(viewModel.userChecked) {
            onLoading = {
                progressDialog = DialogWindow.progressCircle(requireContext(), "Loading...", true)
            }

            onSuccess = { user ->
                progressDialog?.dismiss()
                binding.includeDaftarJual.daftarJualSaya.isVisible = true
                binding.includeDaftarJual.txtName.text = user.fullName
                binding.includeDaftarJual.txtCity.text = user.city
                Glide.with(binding.root).load(user.imageUrl).centerCrop().into(binding.includeDaftarJual.ivAkun)

                binding.includeDaftarJual.btnEditProfile.setOnClickListener {
                    val intent = Intent(requireActivity(), UpdateAkunActivity::class.java)
                    intent.putExtra("showData", user)
                    startActivity(intent)
                }
            }

            onFailure = { _, _ ->
                progressDialog?.dismiss()
                binding.txtTryLogin.isVisible = true
                with(binding.btnTryLogin) {
                    isVisible = true
                    setOnClickListener {
                        findNavController().navigate(DaftarJualFragmentDirections.actionNavigationDaftarJualToLoginFragment())
                    }
                }
                binding.includeDaftarJual.daftarJualSaya.isVisible = false
            }

        }
        val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
        binding.includeDaftarJual.viewPager.adapter = sectionPagerAdapter

        with(binding.includeDaftarJual) {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.product,
            R.string.diminati,
            R.string.terjual
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}