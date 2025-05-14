package ru.itmo.se.mad.ui.main.water

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