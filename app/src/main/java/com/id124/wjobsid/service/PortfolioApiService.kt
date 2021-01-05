package com.id124.wjobsid.service

import com.id124.wjobsid.model.portfolio.PortfolioResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PortfolioApiService {
    @GET("portfolio/{enId}")
    suspend fun getAllPortfolio(
        @Path("enId") enId: Int
    ): PortfolioResponse

    @Multipart
    @POST("portfolio")
    suspend fun createPortfolio(
        @Part("en_id") enId: RequestBody,
        @Part("pr_app") prApp: RequestBody,
        @Part("pr_description") prDescription: RequestBody,
        @Part("pr_link_pub") prLinkPub: RequestBody,
        @Part("pr_link_repo") prLinkRepo: RequestBody,
        @Part("pr_work_place") prWorkPlace: RequestBody,
        @Part("pr_type") prType: RequestBody,
        @Part image: MultipartBody.Part
    ): PortfolioResponse

    @Multipart
    @PUT("portfolio/{prId}")
    suspend fun updatePortfolio(
        @Path("prId") prId: Int,
        @Part("pr_app") prApp: RequestBody,
        @Part("pr_description") prDescription: RequestBody,
        @Part("pr_link_pub") prLinkPub: RequestBody,
        @Part("pr_link_repo") prLinkRepo: RequestBody,
        @Part("pr_work_place") prWorkPlace: RequestBody,
        @Part("pr_type") prType: RequestBody,
        @Part image: MultipartBody.Part
    ): PortfolioResponse

    @DELETE("portfolio/{prId}")
    suspend fun deletePortfolio(
        @Path("prId") prId: Int
    ): PortfolioResponse
}