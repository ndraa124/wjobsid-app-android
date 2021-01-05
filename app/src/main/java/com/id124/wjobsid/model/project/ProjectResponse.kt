package com.id124.wjobsid.model.project

import com.google.gson.annotations.SerializedName

data class ProjectResponse(val success: Boolean, val message: String, val data: List<ProjectItem>) {
    data class ProjectItem(
        @SerializedName("pj_id")
        val pjId: Int,

        @SerializedName("cn_id")
        val cnId: Int,

        @SerializedName("pj_project_name")
        val pjProjectName: String,

        @SerializedName("pj_description")
        val pjDescription: String,

        @SerializedName("pj_deadline")
        val pjDeadline: String,

        @SerializedName("pj_image")
        val pjImage: String
    )
}