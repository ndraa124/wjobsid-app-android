package com.id124.wjobsid.model.hire

import com.google.gson.annotations.SerializedName

data class HireResponse(val success: Boolean, val message: String, val data: List<HireItem>) {
    data class HireItem(
        @SerializedName("hr_id")
        val hrId: Int,

        @SerializedName("en_id")
        val enId: Int,

        @SerializedName("pj_id")
        val pjId: Int,

        @SerializedName("hr_price")
        val hrPrice: Long,

        @SerializedName("hr_message")
        val hrMessage: String,

        @SerializedName("hr_status")
        val hrStatus: String,

        @SerializedName("hr_date_confirm")
        val hrDateConfirm: String,

        @SerializedName("pj_project_name")
        val pjProjectName: String,

        @SerializedName("pj_description")
        val pjDescription: String,

        @SerializedName("pj_deadline")
        val pjDeadline: String,

        @SerializedName("pj_image")
        val pjImage: String,

        @SerializedName("cn_company")
        val cnCompany: String,

        @SerializedName("cn_field")
        val cnField: String,

        @SerializedName("cn_city")
        val cnCity: String,

        @SerializedName("cn_profile")
        val cnProfile: String,

        @SerializedName("en_profile")
        val enProfile: String,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("ac_phone")
        val acPhone: String
    )
}