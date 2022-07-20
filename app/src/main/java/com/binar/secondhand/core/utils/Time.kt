package com.binar.secondhand.core.utils

import java.text.SimpleDateFormat

fun String.dateformat():String{
    val inputtimeformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outtimeformat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val dateformat = inputtimeformat.parse(this)
    val date = outtimeformat.format(dateformat)
    return date
}