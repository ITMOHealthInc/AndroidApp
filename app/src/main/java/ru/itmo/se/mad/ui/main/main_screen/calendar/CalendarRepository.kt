package ru.itmo.se.mad.ui.main.main_screen.calendar

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.itmo.se.mad.ui.main.stepsActivity.fit.FitApiService

interface CalendarRepository {
    @GET("activity/month-steps")
    fun getSteps(): Call<FitApiService.StepsResponse>

}