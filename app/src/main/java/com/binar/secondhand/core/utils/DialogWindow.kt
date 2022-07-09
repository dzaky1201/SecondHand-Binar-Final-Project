package com.binar.secondhand.core.utils

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.binar.secondhand.R

object DialogWindow {
    inline val Context.layoutInflater: android.view.LayoutInflater
        get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

    fun progressCircle(context: Context, msg : String = "Please wait ...", isShow : Boolean) : AlertDialog {

        val builder = AlertDialog.Builder(context, R.style.NormalDialog)
        val view = context.layoutInflater.inflate(R.layout.progress_dialog, null)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(view)
        //builder.setView(R.layout.progress_dialog)
        val dialog = builder.create()
        val txtMessage: TextView? = view.findViewById(R.id.txtMessage)

        txtMessage?.text = msg

        if (isShow)
            dialog.show()
        else
            dialog.dismiss()

        return dialog
    }
}