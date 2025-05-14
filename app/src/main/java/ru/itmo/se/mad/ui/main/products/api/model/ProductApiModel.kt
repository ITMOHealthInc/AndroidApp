package ru.itmo.se.mad.ui.main.products.api.model

data class ProductApiModel (
    val id: Int?,
    val name: String?,
    val affiliation: String,
    val water: Float?,
    val mass: Float,
    val fiber: Float,
    val sugar: Float,
    val saturatedFat: Float,
    val salt: Float,
    val kbzhu: Kbzhu
)
