package ru.itmo.se.mad.api.products

import kotlinx.serialization.Serializable
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.ui.alert.AlertManager

data class DailySummaryResponse(
    val date: String,
    val totalWater: Float,
    val totalKbzhu: Kbzhu
)

data class Kbzhu(
    val calories: Float,
    val proteins: Float,
    val fats: Float,
    val carbohydrates: Float
)

@Serializable
data class MealRequest(
    val type: String,
    val productIds: List<Int>,
    val recipeIds: List<Int>
)

suspend fun sendWaterMeal(milliliters: Float) {
    val jwtToken: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo1MDAwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6NTAwMCIsInVzZXJuYW1lIjoidXNlciJ9.PXFU57PS94Da36MEVmnbSUIdo9UrJuRCP496Bipn8a0"
    val request = MealRequest(
        type = "SNACK",
        productIds = List(milliliters.toInt()) { 1 },
        recipeIds = listOf()
    )

    val bearerToken = "Bearer $jwtToken"
    try {
        ApiClient.productsApi.sendWaterMeal(bearerToken, request)
    } catch (e: Exception) {
        AlertManager.error("Ошибка при отправке, проверьте соединение с интернетом")
    }
}