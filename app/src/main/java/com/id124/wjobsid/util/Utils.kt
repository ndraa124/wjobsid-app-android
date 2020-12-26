package com.id124.wjobsid.util

import android.content.Context
import android.text.TextUtils

class Utils(private val context: Context) {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}