package com.id124.wjobsid.model.account

import com.google.gson.annotations.SerializedName

data class AccountResponse(val success: String, val message: String, val data: AccountItem) {
    data class AccountItem(
        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("ac_phone")
        val acPhone: String,

        @SerializedName("ac_password")
        val acPassword: String,

        @SerializedName("ac_level")
        val acLevel: Int,

        @SerializedName("token")
        val token: String
    )
}