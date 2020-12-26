package com.id124.wjobsid.service

import com.id124.wjobsid.model.engineer.EngineerResponse
import retrofit2.http.GET

interface EngineerApiService {
    @GET("engineer")
    suspend fun getAllEngineer(): EngineerResponse
}