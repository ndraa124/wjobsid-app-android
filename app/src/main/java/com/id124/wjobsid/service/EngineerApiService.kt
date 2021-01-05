package com.id124.wjobsid.service

import com.id124.wjobsid.model.engineer.EngineerResponse
import retrofit2.http.*

interface EngineerApiService {
    @GET("engineer")
    suspend fun getAllEngineer(
        @Query("search") search: String? = null,
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
}