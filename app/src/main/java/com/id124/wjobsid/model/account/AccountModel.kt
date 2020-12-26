package com.id124.wjobsid.model.account

data class AccountModel(
    val acId: Int? = 0,
    val acName: String? = null,
    val acEmail: String? = null,
    val acPhone: String? = null,
    val acPassword: String? = null,
    val acLevel: Int? = 0,
    val token: String? = null
)