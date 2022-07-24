package com.binar.secondhand.screen.preview_product

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.binar.secondhand.MainActivity
import com.binar.secondhand.R
import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.databinding.FragmentPreviewBinding
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.jual.add_product.AddProductActivity
import com.binar.secondhand.screen.jual.add_product.AddProductViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: FragmentPreviewBinding
    private val viewModel by viewModel<AddProductViewModel>()
    private val viewModelAkun: AkunViewModel by viewModel()

    val imageProduct : File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelAkun.getUser()
        val userManager = viewModelAkun.userManager

        val bundle = intent.extras
        with(binding){


            with(viewModel.stateCategories) {
                onSuccess = { categories ->
                    val categoryName = categories.map { it.name }
                    val categoryId = categories.map { it.id }

                    for (i in categoryName.indices) {
                        val h = KeyPairBoolData()
                        h.id = categoryId[i]!!.toLong()
                        h.isSelected = i < 5
                        tvCategory.setText(h.name)

                    }
                }
            }
            userManager.onSuccess = {
                tvNameSeller.setText(it.fullName)
                Glide.with(this@PreviewActivity).load(it.imageUrl).into(ivSellerProfile)
            }
            tvName.setText(bundle?.getString("namaProduk"))
            tvDescription.setText(bundle?.getString("nameDescription"))
            tvCity.setText(bundle?.getString("nameAddress"))
            tvHarga.setText(bundle?.getString("productPrice"))
            Glide.with(this@PreviewActivity).load(bundle?.getString("imageProduct")).into(ivProduct)


            icBack.setOnClickListener {
                super.onBackPressed()
            }

            btnUpload.setOnClickListener {
               var nameProduct = bundle?.getString("namaProduk")
               var description = bundle?.getString("nameDescription")
                var idCategory = bundle?.getStringArrayList("listIdCategory")
                var city = bundle?.getString("nameAddress")
               var productPrice = bundle?.getString("productPrice")
               var imageProduct : File = File(bundle?.getString("imageProduct"))
                val postProduk = SellerProductRequest(
                    name = nameProduct,
                    basePrice = productPrice,
                    categorId = idCategory!!,
                    location = city,
                    description = description
                )

                viewModel.postProduct(postProduk, imageProduct)
            }

            with(viewModel.productPostStateEventManager) {
                onLoading = {
                    binding.btnUpload.setText("Sedang Mengupload..")
                    binding.btnUpload.setBackgroundResource(R.drawable.shape_btn_detail_two)
                    binding.btnUpload.isClickable = false
                }

                onSuccess = {
                    val intent = Intent(this@PreviewActivity, MainActivity::class.java)
                    startActivity(intent)
                    onBackPressed()
                    Toast.makeText(
                        this@PreviewActivity,
                        "Produk Berhasil Ditambahkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                onFailure = { _, _ ->
                    binding.btnUpload.setText("Upload Ulang..")
                    Toast.makeText(
                        this@PreviewActivity,
                        "Produk Gagal Diupload",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}