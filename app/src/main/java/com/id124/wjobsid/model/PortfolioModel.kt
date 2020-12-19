package com.id124.wjobsid.model

data class PortfolioModel(
    val pr_id: Int? = 0,
    val en_id: Int? = 0,
    val pr_app: String? = null,
    val pr_description: String? = null,
    val pr_link_pub: String? = null,
    val pr_link_repo: String? = null,
    val pr_work_place: String? = null,
    val pr_type: String? = null,
    val pr_image: String? = null
)