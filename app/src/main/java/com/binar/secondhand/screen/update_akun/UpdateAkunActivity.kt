package com.binar.secondhand.screen.update_akun

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
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.databinding.ActivityUpdateAkunBinding
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

class UpdateAkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAkunBinding
    private val viewModel: UpdateAkunViewModel by viewModel()
    private var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val args = intent.getParcelableExtra<User>("showData")
        if (args != null) {
            binding.apply {
                edtFullName.setText(args.fullName)
                edtEmail.setText(args.email)
                edtPhoneNumber.setText(args.phoneNumber)
                edtAddress.setText(args.address)
                edtCity.setText(args.city)
                Glide.with(this@UpdateAkunActivity).load(args.imageUrl).centerCrop().into(binding.imgProfile)
            }
        }

        val updateUserManager = viewModel.updateUserManager

        updateUserManager.onLoading = {
            binding.loadingUpdate.isVisible = true
        }
        updateUserManager.onSuccess = {
            onBackPressed()
            Toast.makeText(this@UpdateAkunActivity, "User Berhasil Diupdate", Toast.LENGTH_LONG)
                .show()
        }

        updateUserManager.onFailure = { _, _ ->
            Toast.makeText(this, "Gagal Dalam Update Data", Toast.LENGTH_SHORT).show()
            binding.loadingUpdate.isVisible = false
        }

        binding.imgProfile.setOnClickListener {
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
            binding.btnUpdate.setOnClickListener {
                val fullname = edtFullName.text.toString()
                val email = edtEmail.text.toString()
                val phoneNumber = edtPhoneNumber.text.toString()
                val address = edtAddress.text.toString()
                val city = edtCity.text.toString()
                val password = edtPassword.text.toString()
                val image = fileImage
                if (image == null) {
                    viewModel.updateUser(
                        fullname,
                        email,
                        password,
                        address,
                        phoneNumber,
                        city
                    )
                } else {
                    viewModel.updateUserWithImage(
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
                    EasyImage.openCamera(this@UpdateAkunActivity, 1)
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
                    EasyImage.openGallery(this@UpdateAkunActivity, 2)
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
                    Glide.with(this@UpdateAkunActivity).load(imageFile).into(binding.imgProfile)

                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}