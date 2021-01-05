package com.id124.wjobsid.model.experience

import com.google.gson.annotations.SerializedName

data class ExperienceResponse(val success: Boolean, val message: String, val data: List<ExperienceItem>) {
    data class ExperienceItem(
        @SerializedName("ex_id")
        val exId: Int,

        @SerializedName("en_id")
        val enId: Int,

        @SerializedName("ex_position")
        val exPosition: String,

        @SerializedName("ex_company")
        val exCompany: String,

        @SerializedName("ex_start")
        val exStart: String,

        @SerializedName("ex_end")
        val exEnd: String,

        @SerializedName("ex_description")
        val exDescription: String
    )
}