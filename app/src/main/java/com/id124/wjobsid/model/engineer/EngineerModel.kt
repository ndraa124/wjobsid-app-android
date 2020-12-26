package com.id124.wjobsid.model.engineer

data class EngineerModel(
    val enId: String,
    val acId: String,
    val acName: String,
    val enJobTitle: String? = null,
    val enJobType: String? = null,
    val enDomicile: String? = null,
    val enDescription: String? = null,
    val enProfile: String? = null
)