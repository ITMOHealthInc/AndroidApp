package ru.itmo.se.mad.ui.main.water

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MealApiService {
    @POST("products/meals")
    suspend fun sendWaterMeal(
        @Header("Authorization") token: String,
        @Body request: MealRequest
    )
    @GET("meals/daily-summary")
    suspend fun getDailySummary(
        @Header("Authorization") token: String
    ): DailySummaryResponse
}