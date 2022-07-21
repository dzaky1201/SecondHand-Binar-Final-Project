package com.binar.secondhand.screen.home

import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.secondhand.R
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.utils.*
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.binar.secondhand.databinding.ItemHomeListProductsBinding
import com.binar.secondhand.screen.home.adapter.CategoryAdapter
import com.binar.secondhand.screen.home.adapter.ImageSlideAdapter
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.*


class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()
    private var progressDialog: AlertDialog? = null
    private val productPagingAdapter by genericAdapterLazy<Product>(
        layoutRes = R.layout.item_home_list_products,
        onBind = { _, item ->
            onBindData(item)
        }
    )

    private var currentPage = 1
    private var currentPageCategory = 1
    private var currentPageSearch = 1
    private var stateCategoryProduct = false

    private fun View.onBindData(
        item: Product
    ) {
        ItemHomeListProductsBinding.bind(this).apply {
            root.setOnClickListener {
                it.findNavController()
                    .navigate(HomeFragmentDirections.actionNavigationHomeToDetailFragment(item.id))
            }

            val categoryName = arrayListOf<String>()

            item.categories.forEach {
                categoryName.add(it.name)
            }

            val categories = categoryName.joinToString()
            Log.d("CategoryName", categories)
            tvTitle.text = item.name
            tvPrice.text = item.basePrice.toDouble().formatRupiah()

            tvCategory.text = categories.replace(", ", "\n")



            Glide.with(binding.root).load(item.imageUrl)
                .error(R.drawable.home_attribute)
                .into(ivPosterImage)
        }
    }


    private fun setByCategory(categoryId: Int) {
        viewModel.getCategory(categoryId, currentPageCategory)
        binding.rvListProducts.onReachBottomScroll(viewModel.categoryStateEvent) {
            viewModel.getCategory(categoryId, currentPageCategory)
            with(viewModel.categoryStateEvent) {
                onLoading = {
                    productPagingAdapter.pushLoading()
                }
                onSuccess = {
                    progressDialog?.dismiss()
                    productPagingAdapter.pushItems(it)
                    currentPageCategory += 1
                }
                onFailure = { _, _ ->
                    progressDialog?.dismiss()
                    Log.d("Failed", "Category Failed")
                }
            }
        }

        with(viewModel.categoryStateEvent) {
            onLoading = {
                productPagingAdapter.pushLoading()
            }
            onSuccess = {
                progressDialog?.dismiss()
                productPagingAdapter.pushItemNew(it)
            }
            onFailure = { _, _ ->
                progressDialog?.dismiss()
                Log.d("Failed", "Category Failed")
            }
        }
    }


    override fun inflaterBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateBinding() {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel.getCategories()
        viewModel.getBanner()
        viewModel.getProducts(currentPage)

        // adapter for products
        binding.rvListProducts.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvListProducts.adapter = productPagingAdapter

        // adapter for categories
        val categoriesAdapter = CategoryAdapter { id, page ->
            currentPage = page
            currentPageCategory = page
            stateCategoryProduct = true
            setByCategory(id)
        }

        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        val productEventManager = viewModel.productStateEvent
        binding.rvListProducts.onReachBottomScroll(productEventManager) {
            if (!stateCategoryProduct) {
                viewModel.getProducts(currentPage)
            }

        }

        with(viewModel.bannerStateEvent) {
            onSuccess = {
                progressDialog?.dismiss()
                val adapter = ImageSlideAdapter(requireContext(), it)
                binding.viewpager.adapter = adapter
                binding.indicator.setViewPager(binding.viewpager)
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query, currentPageSearch)
                binding.rvListProducts.onReachBottomScroll(viewModel.categoryStateEvent) {
                    viewModel.searchProduct(query, currentPageSearch)
                    with(viewModel.categoryStateEvent) {
                        onLoading = {
                            productPagingAdapter.pushLoading()
                        }
                        onSuccess = {
                            progressDialog?.dismiss()
                            productPagingAdapter.pushItems(it)
                            currentPageSearch += 1
                        }
                        onFailure = { _, _ ->
                            progressDialog?.dismiss()
                            Log.d("Failed", "Category Failed")
                        }
                    }
                }
                with(viewModel.searchStateEvent) {
                    onLoading = {
                        productPagingAdapter.pushLoading()
                    }
                    onSuccess = {
                        progressDialog?.dismiss()
                        productPagingAdapter.pushItemNew(it)

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
                progressDialog =
                    DialogWindow.progressCircle(requireContext(), "Sedang Memuat Product...", true)
            }
            onSuccess = {
                progressDialog?.dismiss()
                categoriesAdapter.submitList(it)
            }
        }

        with(viewModel.productStateEvent) {
            onLoading = {
                productPagingAdapter.pushLoading()
            }
            onSuccess = {
                progressDialog?.dismiss()
                if (!stateCategoryProduct) {
                    productPagingAdapter.pushItems(it)
                    currentPage += 1
                }
            }
            onFailure = { _, _ ->
                progressDialog?.dismiss()
            }
        }
    }
}