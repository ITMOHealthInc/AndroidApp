package ru.itmo.se.mad.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itmo.se.mad.api.auth.AuthApiService
import ru.itmo.se.mad.api.dailySummary.SummaryApiService
import ru.itmo.se.mad.api.water.WaterApiService
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2/"

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

    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val waterApi: WaterApiService by lazy {
        retrofit.create(WaterApiService::class.java)
    }

    val summaryApi: SummaryApiService by lazy {
        retrofit.create(SummaryApiService::class.java)
    }
}