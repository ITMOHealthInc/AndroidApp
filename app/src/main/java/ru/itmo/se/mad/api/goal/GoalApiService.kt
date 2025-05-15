package ru.itmo.se.mad.api.goal

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface GoalApiService {
    @POST("user_goals/goals")
    suspend fun createGoal(@Body request: CreateGoalRequest) : GoalCreatedResponse

    @GET("user_goals/goals")
    suspend fun getGoal() : UserGoal

    @DELETE("user_goals/goals")
    suspend fun deleteGoal()
}