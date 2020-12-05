package com.id124.wjobsid.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {
    companion object {
        const val PREF_NAME = "LOGIN"
        const val AC_LEVEL = "AC_LEVEL"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setLevel(acLevel: Int) {
        editor.putInt(AC_LEVEL, acLevel)
        editor.commit()
    }

    fun getLevel(): Int {
        return sharedPreferences.getInt(AC_LEVEL, 0)
    }
}