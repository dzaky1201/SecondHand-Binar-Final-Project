package com.binar.secondhand.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.binar.secondhand.screen.home.adapter.CategoryAdapter
import com.binar.secondhand.screen.home.adapter.ListProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private var progressDialog: AlertDialog? = null

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

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchProduct(query)
                with(viewModel.searchStateEvent) {
                    onLoading = {
                        progressDialog = DialogWindow.progressCircle(requireContext(), "Sedang Mencari...", true)
                    }
                    onSuccess = {
                        progressDialog?.dismiss()
                        productsAdapter.submitList(it)

                    }
                    onFailure = { _, _ ->
                        progressDialog?.dismiss()
                    }
                }
                return false
            }

        })


        with(viewModel.categoriesStateEvent) {
            onLoading = {
                progressDialog = DialogWindow.progressCircle(requireContext(), "Sedang Memuat Product...", true)
            }
            onSuccess = {
                progressDialog?.dismiss()
                categoriesAdapter.submitList(it)
            }
        }

        with(viewModel.productStateEvent) {
            onSuccess = {
                progressDialog?.dismiss()
                productsAdapter.submitList(it)
            }
            onFailure = { _, _ ->
                progressDialog?.dismiss()
            }
        }
    }

    private fun setByCategory(categoryId: Int, productsAdapter: ListProductsAdapter) {
        viewModel.getCategory(categoryId)

        with(viewModel.categoryStateEvent) {
            onLoading = {
                progressDialog = DialogWindow.progressCircle(requireContext(), "Loading...", true)
            }
            onSuccess = {
                progressDialog?.dismiss()
                productsAdapter.submitList(it)
            }
            onFailure = { _, _ ->
                progressDialog?.dismiss()
                Log.d("Failed", "Category Failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}