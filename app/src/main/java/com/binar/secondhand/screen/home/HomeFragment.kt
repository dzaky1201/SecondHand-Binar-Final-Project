package com.binar.secondhand.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.binar.secondhand.screen.home.adapter.ListProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        val adapter = ListProductsAdapter()
        binding.rvListProducts.layoutManager =  GridLayoutManager(requireContext(),2)
        binding.rvListProducts.adapter = adapter


        with(viewModel.productStateEvent){
            onLoading = {

            }
            onSuccess = {
                adapter.submitList(it)
            }
            onFailure = { _,_->
                print("GAGAL COK")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}