package com.id124.wjobsid.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/"
        const val BASE_URL_IMAGE = BASE_URL + "images/"
        const val BASE_URL_IMAGE_DEFAULT_PROFILE = BASE_URL + "images/default_profile.png"
        const val BASE_URL_IMAGE_DEFAULT_PROFILE_2 = BASE_URL + "images/default_profile_2.png"
        const val BASE_URL_IMAGE_DEFAULT_BACKGROUND = BASE_URL + "images/default_background.png"

        private fun provideHttpLoggingInterceptor() = run {
            HttpLoggingInterceptor().apply {
                apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
        }

        fun getApiClient(context: Context): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(HeaderInterceptor(context))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}