package ru.itmo.se.mad.api.fit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface FitApiService {
    @GET("activity/steps")
    suspend fun getSteps(): Response<StepsResponse>

    @POST("activity/set-steps")
    fun setDailyGoal(@Body goal: Int): Call<Void>

    @POST("activity/set-steps")
    fun setSteps(@Body stepsData: Int): Call<Void>
}