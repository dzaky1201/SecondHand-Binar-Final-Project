package com.binar.secondhand.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val accessToken: String = "",
    val email: String = "",
    val name: String = ""
): Parcelable

