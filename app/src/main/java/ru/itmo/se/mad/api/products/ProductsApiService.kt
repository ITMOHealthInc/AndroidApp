package ru.itmo.se.mad.api.products

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ProductsApiService {
    @GET("products/meals/daily-summary")
    suspend fun getDailySummary(
        @Header("Authorization") token: String
    ): DailySummaryResponse

    @POST("products/meals")
    suspend fun sendWaterMeal(
        @Header("Authorization") token: String,
        @Body request: MealRequest
    )
}