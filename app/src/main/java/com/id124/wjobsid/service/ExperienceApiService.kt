package com.id124.wjobsid.service

import com.id124.wjobsid.model.experience.ExperienceResponse
import retrofit2.http.*

interface ExperienceApiService {
    @GET("experience/{enId}")
    suspend fun getAllExperience(
        @Path("enId") enId: Int
    ): ExperienceResponse

    @FormUrlEncoded
    @POST("experience")
    suspend fun createExperience(
        @Field("en_id") enId: Int,
        @Field("ex_position") exPosition: String,
        @Field("ex_company") exCompany: String,
        @Field("ex_start") exStart: String,
        @Field("ex_end") exEnd: String,
        @Field("ex_description") exDescription: String
    ): ExperienceResponse

    @FormUrlEncoded
    @PUT("experience/{exId}")
    suspend fun updateExperience(
        @Path("exId") exId: Int,
        @Field("ex_position") exPosition: String,
        @Field("ex_company") exCompany: String,
        @Field("ex_start") exStart: String,
        @Field("ex_end") exEnd: String,
        @Field("ex_description") exDescription: String
    ): ExperienceResponse

    @DELETE("experience/{exId}")
    suspend fun deleteExperience(
        @Path("exId") exId: Int
    ): ExperienceResponse
}