package com.id124.wjobsid.service

import com.id124.wjobsid.model.account.AccountResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountApiService {
    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginAccount(
        @Field("email") email: String,
        @Field("password") password: String
    ): AccountResponse

    @FormUrlEncoded
    @POST("account")
    suspend fun signUpEngineerAccount(
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_phone") acPhone: String,
        @Field("ac_password") acPassword: String,
        @Field("ac_level") acLevel: Int
    ): AccountResponse

    @FormUrlEncoded
    @POST("account")
    suspend fun signUpCompanyAccount(
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_phone") acPhone: String,
        @Field("ac_password") acPassword: String,
        @Field("ac_level") acLevel: Int,
        @Field("cn_company") cnCompany: String,
        @Field("cn_position") cnPosition: String
    ): AccountResponse
}