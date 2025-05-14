package ru.itmo.se.mad.api.water

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WaterApiService {
    @POST("products/meals")
    suspend fun sendWaterMeal(
        @Header("Authorization") token: String,
        @Body request: MealRequest
    )
}