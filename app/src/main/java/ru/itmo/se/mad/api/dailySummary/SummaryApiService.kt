package ru.itmo.se.mad.api.dailySummary

import retrofit2.http.GET
import retrofit2.http.Header

interface SummaryApiService {

    @GET("meals/daily-summary")
    suspend fun getDailySummary(
        @Header("Authorization") token: String
    ): DailySummaryResponse
}