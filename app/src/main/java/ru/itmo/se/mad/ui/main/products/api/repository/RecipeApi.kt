package ru.itmo.se.mad.ui.main.products.api.repository

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.itmo.se.mad.ui.main.products.api.model.RecipeApiModel

interface RecipeApi {
    @GET("/recipes")
    fun getRecipes() : Call<List<RecipeApiModel>>

    @POST
    fun addRecipe() : Call<RecipeApiModel>

    @GET("/recipes/{id}")
    fun getRecipeById(id: Int?) : Call<RecipeApiModel>

    @PUT("/recipes/{id}")
    fun updateRecipeById(id: Int?) : Call<RecipeApiModel>

    @DELETE("/recipes/{id}")
    fun deleteRecipeById(id: Int?) : Call<Void>
}