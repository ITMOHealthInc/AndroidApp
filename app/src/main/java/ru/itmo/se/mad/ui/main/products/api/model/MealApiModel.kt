package ru.itmo.se.mad.ui.main.products.api.model

import kotlinx.datetime.LocalDateTime
import ru.itmo.se.mad.ui.main.products.model.Product
import ru.itmo.se.mad.ui.main.products.model.Recipe

data class MealApiModel (
    val id: Int?,
    val type: String?,
    val addedAt: LocalDateTime?,
    val username: String?,
    val products: List<Product>,
    val recipes: List<Recipe>
)