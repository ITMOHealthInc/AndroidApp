package ru.itmo.se.mad.api.goal

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface GoalApiService {
    @POST("user_goals/user-goals")
    suspend fun createGoal(@Body request: CreateUserGoalRequest) : UserGoalDto

    @GET("user_goals/user-goals")
    suspend fun getGoal() : UserGoalDto

    @PUT("user_goals/user-goals")
    suspend fun updateGoal(@Body request: CreateUserGoalRequest) : UserGoalDto

    @DELETE("user_goals/user-goals")
    suspend fun deleteGoal()
}