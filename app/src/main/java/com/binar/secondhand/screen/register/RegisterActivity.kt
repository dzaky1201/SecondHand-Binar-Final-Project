package com.binar.secondhand.screen.register

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.secondhand.MainActivity
import com.binar.secondhand.R
import com.binar.secondhand.databinding.ActivityRegisterBinding
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

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModel<RegisterViewModel>()
    private var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val updateUserManager = viewModel.registerUserManager

        updateUserManager.onLoading = {
            binding.loadingUpdate.isVisible = true
        }
        updateUserManager.onSuccess = {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_LONG)
                .show()
        }

        updateUserManager.onFailure = { _, _ ->
            Toast.makeText(this, "Register Gagal !", Toast.LENGTH_SHORT).show()
            binding.loadingUpdate.isVisible = false
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
            btnRegister.setOnClickListener {
                val fullname = edtFullName.text.toString()
                val email = edtEmail.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()
                val address = edtAddress.text.toString()
                val city = edtCity.text.toString()
                val password = edtPassword.text.toString()
                val image = fileImage
                if (image == null) {
                    Toast.makeText(this@RegisterActivity, "Masukan Gambar !", Toast.LENGTH_SHORT)
                        .show()
                } else if (fullname.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Masukan Nama Lengkap Anda !",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Masukan Email !", Toast.LENGTH_SHORT)
                        .show()
                } else if (phoneNumber.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Masukan No HP !", Toast.LENGTH_SHORT)
                        .show()
                } else if (address.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Masukan Alamat Anda !",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (city.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Masukan Kota Anda !", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.isEmpty() && password.length < 6) {
                    Toast.makeText(this@RegisterActivity, "Masukan Password", Toast.LENGTH_SHORT)
                        .show()
                } else if (password.length < 6) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Masukan Minimal 6 Karakter",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    viewModel.registerUser(
                        fullname,
                        email,
                        password,
                        address,
                        phoneNumber,
                        city,
                        image
                    )
                }

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
                    EasyImage.openCamera(this@RegisterActivity, 1)
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
                    EasyImage.openGallery(this@RegisterActivity, 2)
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
                    Glide.with(this@RegisterActivity).load(imageFile).into(binding.profileView)

                }
            })
    }
}