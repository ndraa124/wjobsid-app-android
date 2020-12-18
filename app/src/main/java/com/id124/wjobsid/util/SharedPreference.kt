package com.id124.wjobsid.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.id124.wjobsid.activity.main.MainActivity
import com.id124.wjobsid.activity.onboarding.OnboardingActivity

class SharedPreference(private val context: Context) {
    companion object {
        const val PREF_NAME = "LOGIN"
        const val LOGIN = "IS_LOGIN"
        const val TOKEN = "TOKEN"
        const val AC_LEVEL = "AC_LEVEL"
        const val AC_DETAIL = "AC_DETAIL"
        const val AC_EMAIL = "AC_EMAIL"
        const val AC_NAME = "AC_NAME"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun createInDetail(acDetail: Int) {
        editor.putInt(AC_DETAIL, acDetail)
        editor.commit()
    }

    fun createAccountUser(acLevel: Int, acName: String, acEmail: String, token: String) {
        editor.putBoolean(LOGIN, true)
        editor.putInt(AC_LEVEL, acLevel)
        editor.putString(AC_NAME, acName)
        editor.putString(AC_EMAIL, acEmail)
        editor.putString(TOKEN, token)
        editor.commit()
    }

    fun getAccountUser(): HashMap<String, String> {
        val user: HashMap<String, String> = HashMap()
        user[AC_NAME] = sharedPreferences.getString(AC_NAME, "Not set")!!
        user[AC_EMAIL] = sharedPreferences.getString(AC_EMAIL, "Not set")!!
        user[TOKEN] = sharedPreferences.getString(TOKEN, "Not set")!!

        return user
    }

    fun getLevelUser(): Int {
        return sharedPreferences.getInt(AC_LEVEL, 0)
    }

    fun getInDetail(): Int {
        return sharedPreferences.getInt(AC_DETAIL, 0)
    }

    fun getIsLogin(): Boolean {
        return sharedPreferences.getBoolean(LOGIN, false)
    }

    fun checkIsLogin() {
        if (!this.getIsLogin()) {
            context.startActivity(Intent(context, OnboardingActivity::class.java))
            (context as MainActivity).finish()
        }
    }

    fun accountLogout() {
        editor.clear()
        editor.commit()

        context.startActivity(Intent(context, OnboardingActivity::class.java))
        (context as MainActivity).finish()
    }
}