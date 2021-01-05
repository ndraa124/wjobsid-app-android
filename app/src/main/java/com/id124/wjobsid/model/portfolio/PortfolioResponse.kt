package com.id124.wjobsid.model.portfolio

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(val success: Boolean, val message: String, val data: List<PortfolioItem>) {
    data class PortfolioItem(
        @SerializedName("pr_id")
        val prId: Int,

        @SerializedName("en_id")
        val enId: Int,

        @SerializedName("pr_app")
        val prApp: String,

        @SerializedName("pr_description")
        val prDescription: String,

        @SerializedName("pr_link_pub")
        val prLinkPub: String,

        @SerializedName("pr_link_repo")
        val prLinkRepo: String,

        @SerializedName("pr_work_place")
        val prWorkPlace: String,

        @SerializedName("pr_type")
        val prType: String,

        @SerializedName("pr_image")
        val prImage: String
    )
}