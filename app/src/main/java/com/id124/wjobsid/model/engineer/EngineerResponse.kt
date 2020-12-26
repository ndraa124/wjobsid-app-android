package com.id124.wjobsid.model.engineer

import com.google.gson.annotations.SerializedName

class EngineerResponse(val success: String, val message: String, val data: ArrayList<EngineerItem>) {
    data class EngineerItem(
        @SerializedName("en_id")
        val enId: String,

        @SerializedName("ac_id")
        val acId: String,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("en_job_title")
        val enJobTitle: String,

        @SerializedName("en_job_type")
        val enJobType: String,

        @SerializedName("en_domicile")
        val enDomicile: String,

        @SerializedName("en_description")
        val enDescription: String,

        @SerializedName("en_profile")
        val enProfile: String
    )
}