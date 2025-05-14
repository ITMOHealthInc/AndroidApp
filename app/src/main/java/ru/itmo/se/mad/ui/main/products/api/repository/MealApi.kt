package ru.itmo.se.mad.ui.main.products.api.repository

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.itmo.se.mad.ui.main.products.api.model.MealApiModel

interface MealApi {
    @GET("/meals")
    fun getMeals() : Call<List<MealApiModel>>

    @POST("/meals")
    fun addMeal() : Call<MealApiModel>

    @GET("/meals/{id}")
    fun getMealById(id: Int?) : Call<MealApiModel>

    @PUT("/meals/{id}")
    fun updateMealById(id: Int?) : Call<MealApiModel>

    @DELETE("/meals/{id}")
    fun deleteMealById(id: Int?) : Call<MealApiModel>
}