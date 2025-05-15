package ru.itmo.se.mad.api.products

import android.util.Log
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
data class WaterRequest(
    val waterAmount: Int
)

suspend fun sendWaterMeal(milliliters: Float) {
    val request = WaterRequest(
        waterAmount = milliliters.toInt()
    )
    try {
        ApiClient.productsApi.sendWaterMeal(request)
    } catch (e: Exception) {
        Log.e("dbg", "Ошибка при загрузке: ${e.localizedMessage}", e)

        AlertManager.error("Ошибка при отправке, проверьте соединение с интернетом")
    }
}