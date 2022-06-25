package com.binar.secondhand.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel.getProducts()
        viewModel.getCategories()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchProduct(newText)
                with(viewModel.searchStateEvent) {
                    onLoading = {

                    }
                    onSuccess = {
                        val productsAdapter = ListProductsAdapter(it)
                        binding.rvListProducts.layoutManager =
                            GridLayoutManager(requireContext(), 2)
                        binding.rvListProducts.adapter = productsAdapter

                    }
                    onFailure = { _, _ ->
                        print("GAGAL COK")
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
//
                return false
            }

        })

        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)


        with(viewModel.categoriesStateEvent) {
            onSuccess = {
                val categoriesAdapter = CategoryAdapter(it, clicked = {
                    it.id?.let { it1 -> setByCategory(it1) }
                })
                binding.rvCategories.adapter = categoriesAdapter
            }
        }

        with(viewModel.productStateEvent) {
            onLoading = {

            }
            onSuccess = {
                val productsAdapter = ListProductsAdapter(it)
                binding.rvListProducts.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvListProducts.adapter = productsAdapter

            }
            onFailure = { _, _ ->
            }
        }
    }

    private fun setByCategory(categoryId : Int) {
        viewModel.getCategory(categoryId)

        with(viewModel.categoryStateEvent){
            onLoading = {

            }
            onSuccess = {
                val productsAdapter = ListProductsAdapter(it)
                binding.rvListProducts.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvListProducts.adapter = productsAdapter
            }
            onFailure = { _, _ ->
                Log.d("Failed","Category Failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}