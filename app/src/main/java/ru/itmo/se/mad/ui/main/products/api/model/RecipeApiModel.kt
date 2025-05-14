package ru.itmo.se.mad.ui.main.products.api.model

import ru.itmo.se.mad.ui.main.products.model.Product

data class RecipeApiModel (
    val id: Int?,
    val name: String?,
    val username: String?,
    val products: List<Product>
)