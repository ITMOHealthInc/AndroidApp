package ru.itmo.se.mad.ui.main.products.model

data class Product(
    val name : String?,
    val affiliation: String?,
    val water: Float?,
    val mass: Float?,
    val proteins: Float,
    val fiber: Float,
    val sugar: Float,
    val saturatedFat: Float,
    val salt: Float
)
