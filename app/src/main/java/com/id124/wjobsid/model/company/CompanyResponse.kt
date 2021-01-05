package com.id124.wjobsid.model.company

import com.google.gson.annotations.SerializedName

data class CompanyResponse(val success: Boolean, val message: String, val data: List<CompanyItem>) {
    data class CompanyItem(
        @SerializedName("cn_id")
        val cnId: Int,

        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("cn_company")
        val cnCompany: String,

        @SerializedName("cn_position")
        val cnPosition: String,

        @SerializedName("cn_field")
        val cnField: String,

        @SerializedName("cn_city")
        val cnCity: String,

        @SerializedName("cn_description")
        val cnDescription: String,

        @SerializedName("cn_instagram")
        val cnInstagram: String,

        @SerializedName("cn_linkedin")
        val cnLinkedin: String,

        @SerializedName("cn_profile")
        val cnProfile: String
    )
}