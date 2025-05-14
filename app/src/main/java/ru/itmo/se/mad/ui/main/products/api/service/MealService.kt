package ru.itmo.se.mad.ui.main.products.api.service

import retrofit2.Call
import ru.itmo.se.mad.ui.main.products.api.model.MealApiModel
import ru.itmo.se.mad.ui.main.products.api.repository.MealApi

class MealService : MealApi {
    override fun getMeals(): Call<List<MealApiModel>> {
        TODO("Not yet implemented")
    }

    override fun addMeal(): Call<MealApiModel> {
        TODO("Not yet implemented")
    }

    override fun getMealById(id: Int?): Call<MealApiModel> {
        TODO("Not yet implemented")
    }

    override fun updateMealById(id: Int?): Call<MealApiModel> {
        TODO("Not yet implemented")
    }

    override fun deleteMealById(id: Int?): Call<MealApiModel> {
        TODO("Not yet implemented")
    }
}