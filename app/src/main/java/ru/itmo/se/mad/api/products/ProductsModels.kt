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
    val request = MealRequest(
        type = "SNACK",
        productIds = List(milliliters.toInt()) { 1 },
        recipeIds = listOf()
    )
    try {
        ApiClient.productsApi.sendWaterMeal(request)
    } catch (e: Exception) {
        AlertManager.error("Ошибка при отправке, проверьте соединение с интернетом")
    }
}