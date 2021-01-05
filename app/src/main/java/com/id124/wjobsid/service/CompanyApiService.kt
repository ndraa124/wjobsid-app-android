package com.id124.wjobsid.service

import com.id124.wjobsid.model.company.CompanyResponse
import retrofit2.http.*

interface CompanyApiService {
    @GET("company")
    suspend fun getAllCompany(): CompanyResponse

    @GET("company/detail/{acId}")
    suspend fun getDetailCompany(
        @Path("acId") acId: Int
    ): CompanyResponse

    @FormUrlEncoded
    @PUT("company/{cnId}")
    suspend fun updateCompany(
        @Path("cnId") cnId: Int,
        @FieldMap fields: Map<String, String>
    ): CompanyResponse
}