package ru.itmo.se.mad.api.water

import android.util.Log
import kotlinx.serialization.Serializable
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.exception.VisibleException
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.alert.AlertType


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
        ApiClient.waterApi.sendWaterMeal(bearerToken, request)
    } catch (e: Exception) {
        AlertManager.show(VisibleException(AlertType.WARNING, "Ошибка при отправке, проверьте соединение с интернетом"))
    }
}