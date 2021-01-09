package com.id124.wjobsid.service

import com.id124.wjobsid.model.engineer.EngineerResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface EngineerApiService {
    @GET("engineer")
    suspend fun getAllEngineer(
        @Query("search") search: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): EngineerResponse

    @GET("engineer/filter")
    suspend fun getFilterEngineer(
        @Query("filter") filter: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): EngineerResponse

    @GET("engineer/detail/{acId}")
    suspend fun getDetailEngineer(
        @Path("acId") acId: Int
    ): EngineerResponse

    @FormUrlEncoded
    @PUT("engineer/{enId}")
    suspend fun updateEngineer(
        @Path("enId") enId: Int,
        @FieldMap fields: Map<String, String>
    ): EngineerResponse

    @Multipart
    @PUT("engineer/{enId}")
    suspend fun updateEngineerImage(
        @Path("enId") enId: Int,
        @Part image: MultipartBody.Part? = null
    ): EngineerResponse
}