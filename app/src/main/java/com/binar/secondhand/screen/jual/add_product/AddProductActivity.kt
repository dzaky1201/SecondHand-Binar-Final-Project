package com.binar.secondhand.screen.jual.add_product

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.databinding.ActivityAddProductBinding
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private val viewModel by viewModel<AddProductViewModel>()
    private var fileImage: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCategories()
        val listArray1: MutableList<KeyPairBoolData> = ArrayList()
        val idCategory: ArrayList<String> = arrayListOf()

        with(viewModel.stateCategories) {
            onSuccess = { categories ->
                val categoryName = categories.map { it.name }
                val categoryId = categories.map { it.id }

                for (i in categoryName.indices) {
                    val h = KeyPairBoolData()
                    h.id = categoryId[i]!!.toLong()
                    h.name = categoryName[i].toString()
                    h.isSelected = i < 5
                    listArray1.add(h)
                }
            }
        }

        binding.profileView.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItems = arrayOf(
                "Select Photo From Galery",
                "Capture photo from camera"
            )
            pictureDialog.setItems(pictureDialogItems) { _, which ->
                when (which) {
                    0 -> choosePhotoFromGalery()
                    1 -> takePhotoFromCamera()
                }

            }

            pictureDialog.show()
        }
        with(binding) {
            selectCategory.setSearchHint("Categories...")
            selectCategory.isSearchEnabled
            selectCategory.setClearText("Close & Clear")
            selectCategory.setSearchHint("Pilih Category Produk")
            selectCategory.setEmptyTitle("Categori tidak ditemukan")
            selectCategory.setLimit(3) {
                Toast.makeText(
                    applicationContext,
                    "Limit Exceed", Toast.LENGTH_LONG
                ).show()
            }
            selectCategory.setItems(listArray1) { items ->
                for (i in 0 until items.size) {
                    Log.i(
                        "CategorySelected",
                        i.toString() + " : " + items[i].id + " : " + items[i].isSelected
                    )

                    idCategory.add(items[i].id.toString())
                }
            }
        }





        with(binding) {
            btnTerbitkan.setOnClickListener {
                val namaProduk = edtNamaProduk.text.toString()
                val hargaProduk = edtHargaProduk.text.toString()
                val namaDescription = edtDescription.text.toString()
                val namaAddress = edtAddress.text.toString()
                val imageProduk = fileImage
                if (imageProduk == null) {
                    Toast.makeText(this@AddProductActivity, "Masukan Gambar !", Toast.LENGTH_SHORT)
                        .show()
                } else if (namaProduk.isEmpty()) {
                    Toast.makeText(
                        this@AddProductActivity,
                        "Masukan Nama Produk !",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else if (hargaProduk.isEmpty()) {
                    Toast.makeText(this@AddProductActivity, "Masukan Harga !", Toast.LENGTH_SHORT)
                        .show()
                } else if (namaDescription.isEmpty()) {
                    Toast.makeText(
                        this@AddProductActivity,
                        "Masukan Deskripsi Produk !",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else if (namaAddress.isEmpty()) {
                    Toast.makeText(
                        this@AddProductActivity,
                        "Masukan Alamat Anda !",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else if (idCategory.isEmpty()) {
                    Toast.makeText(
                        this@AddProductActivity,
                        "Masukan Alamat Anda !",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val postProduk = SellerProductRequest(
                        name = namaProduk,
                        basePrice = hargaProduk,
                        categorId = idCategory,
                        location = namaAddress,
                        description = namaDescription
                    )

                    viewModel.postProduct(postProduk, imageProduk)

//                    Toast.makeText(this@AddProductActivity, idCategory.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(viewModel.productPostStateEventManager) {
            onLoading = {
                binding.loadingAddProduk.isVisible = true
            }

            onSuccess = {
                binding.loadingAddProduk.isVisible = false
                onBackPressed()
                Toast.makeText(
                    this@AddProductActivity,
                    "Produk Berhasil Ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
            }

            onFailure = { _, _ ->
                binding.loadingAddProduk.isVisible = false
                Toast.makeText(
                    this@AddProductActivity,
                    "Produk Gagal Diupload",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    EasyImage.openCamera(this@AddProductActivity, 1)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermssions()
            }
        }).onSameThread().check()
    }

    private fun choosePhotoFromGalery() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    EasyImage.openGallery(this@AddProductActivity, 2)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRationalDialogForPermssions()
            }
        }).onSameThread().check()
    }

    private fun showRationalDialogForPermssions() {
        AlertDialog.Builder(this).setMessage(
            "" +
                    "It Looks like you have turned off permission required" +
                    "for this feature. It can be enabled under the" +
                    "Applications Settings"
        )
            .setPositiveButton("GO TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    fileImage = imageFile
                    Glide.with(this@AddProductActivity).load(imageFile).into(binding.profileView)

                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
