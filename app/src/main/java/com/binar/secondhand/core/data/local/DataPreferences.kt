package com.binar.secondhand.core.data.local

import android.annotation.SuppressLint
import android.content.Context
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DataPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var token: String
        get() = sharedPreferences.getString(Key.TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit()
                .putString(Key.TOKEN, value)
                .apply()
        }

    @SuppressLint("CommitPrefEdits")
    fun clearSession(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    companion object : KoinComponent {
        const val NAME = "main_pref"

        val get: DataPreferences = get()
    }

    object Key {
        const val TOKEN = "token"
    }
}