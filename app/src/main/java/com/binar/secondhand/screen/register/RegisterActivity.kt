package com.binar.secondhand.screen.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.databinding.ActivityRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val updateUserManager = viewModel.registerUserManager

        updateUserManager.onLoading = {
            binding.loadingUpdate.isVisible = true
        }
        updateUserManager.onSuccess = {
            onBackPressed()
            Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_LONG)
                .show()
        }

        updateUserManager.onFailure = { _, _ ->
            Toast.makeText(this, "Register Gagal !", Toast.LENGTH_SHORT).show()
            binding.loadingUpdate.isVisible = false
        }


        with(binding) {
            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val register = LoginRequest(email, password)
                if (register.email.isNullOrEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Masukan Email !", Toast.LENGTH_SHORT)
                        .show()
                } else if (register.password.isNullOrEmpty() && password.length < 6) {
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
                        register
                    )
                }

            }
        }
        binding.icBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}