package com.id124.wjobsid.service

import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.account.LoginResponse
import com.id124.wjobsid.model.account.SignUpResponse
import retrofit2.http.*

interface AccountApiService {
    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginAccount(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("account")
    suspend fun signUpEngineerAccount(
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_phone") acPhone: String,
        @Field("ac_password") acPassword: String,
        @Field("ac_level") acLevel: Int
    ): SignUpResponse

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
    ): SignUpResponse

    @FormUrlEncoded
    @PUT("account/{acId}")
    suspend fun updateAccount(
        @Path("acId") acId: Int,
        @FieldMap fields: Map<String, String>
    ): AccountResponse

    @GET("account/detail/{acId}")
    suspend fun detailAccount(
        @Path("acId") acId: Int
    ): AccountResponse
}