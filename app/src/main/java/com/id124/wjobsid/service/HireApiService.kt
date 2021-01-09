package com.id124.wjobsid.service

import com.id124.wjobsid.model.hire.HireResponse
import retrofit2.http.*

interface HireApiService {
    @GET("hire/engineer/{enId}")
    suspend fun getAllHire(
        @Path("enId") enId: Int
    ): HireResponse

    @GET("hire/company/{cnId}")
    suspend fun getAllHireCompany(
        @Path("cnId") cnId: Int,
        @Query("status") status: String
    ): HireResponse

    @FormUrlEncoded
    @POST("hire")
    suspend fun createHire(
        @Field("en_id") enId: Int,
        @Field("pj_id") pjId: Int,
        @Field("hr_price") hrPrice: Long,
        @Field("hr_message") hrMessage: String
    ): HireResponse

    @FormUrlEncoded
    @PUT("hire/{hrId}")
    suspend fun updateHireStatus(
        @Path("hrId") hrId: Int,
        @Field("hr_status") hrStatus: String
    ): HireResponse
}