package ru.itmo.se.mad.ui.main.products.api.service

import retrofit2.Call
import ru.itmo.se.mad.ui.main.products.api.model.RecipeApiModel
import ru.itmo.se.mad.ui.main.products.api.repository.RecipeApi

class RecipeService : RecipeApi {
    override fun getRecipes(): Call<List<RecipeApiModel>> {
        TODO("Not yet implemented")
    }

    override fun addRecipe(): Call<RecipeApiModel> {
        TODO("Not yet implemented")
    }

    override fun getRecipeById(id: Int?): Call<RecipeApiModel> {
        TODO("Not yet implemented")
    }

    override fun updateRecipeById(id: Int?): Call<RecipeApiModel> {
        TODO("Not yet implemented")
    }

    override fun deleteRecipeById(id: Int?): Call<Void> {
        TODO("Not yet implemented")
    }
}