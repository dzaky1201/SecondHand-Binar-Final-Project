package com.binar.secondhand.core.utils

import java.text.NumberFormat
import java.util.*

fun Double.formatRupiah(): String{
    val locale = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(locale)
    return format.format(this)
}