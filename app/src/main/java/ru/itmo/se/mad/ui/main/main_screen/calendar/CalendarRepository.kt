package ru.itmo.se.mad.ui.main.main_screen.calendar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CalendarRepository {
    @GET("activity/month-steps")
    fun getMonthSteps(
        @Query("month") month: Int,
        @Query("year") year: Int,
        @Header("Authorization") authHeader: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Accept") accept: String = "application/json"
    ): Call<CalendarApiService.MonthStepsResponse>
}