package ru.itmo.se.mad.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itmo.se.mad.api.auth.AuthApiService
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://localhost/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }
}