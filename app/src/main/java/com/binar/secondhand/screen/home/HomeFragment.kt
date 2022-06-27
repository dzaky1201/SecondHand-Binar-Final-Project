package com.binar.secondhand.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.binar.secondhand.screen.home.adapter.CategoryAdapter
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

        // adapter for products
        val productsAdapter = ListProductsAdapter()
        binding.rvListProducts.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvListProducts.adapter = productsAdapter

        // adapter for categories
        val categoriesAdapter = CategoryAdapter {
            setByCategory(it.id ?: 0, productsAdapter)
        }
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getProducts()
        viewModel.getCategories()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchProduct(newText)
                with(viewModel.searchStateEvent) {
                    onLoading = {
                        binding.loadingHome.isVisible = true
                    }
                    onSuccess = {
                        binding.loadingHome.isVisible = false
                        productsAdapter.submitList(it)

                    }
                    onFailure = { _, _ ->
                        binding.loadingHome.isVisible = false
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
//
                return false
            }

        })


        with(viewModel.categoriesStateEvent) {
            onSuccess = {
                categoriesAdapter.submitList(it)
            }
        }

        with(viewModel.productStateEvent) {
            onLoading = {
                binding.loadingHome.isVisible = true
            }
            onSuccess = {
                binding.loadingHome.isVisible = false
                productsAdapter.submitList(it)
            }
            onFailure = { _, _ ->
                binding.loadingHome.isVisible = false
            }
        }
    }

    private fun setByCategory(categoryId: Int, productsAdapter: ListProductsAdapter) {
        viewModel.getCategory(categoryId)

        with(viewModel.categoryStateEvent) {
            onLoading = {
                binding.loadingHome.isVisible = true
            }
            onSuccess = {
                binding.loadingHome.isVisible = false
                productsAdapter.submitList(it)
            }
            onFailure = { _, _ ->
                Log.d("Failed", "Category Failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}