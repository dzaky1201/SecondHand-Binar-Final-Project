package com.binar.secondhand.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val address: String? = "",
    val createdAt: String? = "",
    val email: String? = "",
    val fullName: String? = "",
    val id: Int? = 0,
    val imageUrl: String? = "",
    val password: String? = "",
    val phoneNumber: String? = "",
    val updatedAt: String? = ""
): Parcelable
