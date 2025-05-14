package ru.itmo.se.mad.ui.main.main_screen.calendar

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.itmo.se.mad.api.ApiClient


class CalendarApiService{
    private val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo1MDAwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6NTAwMCIsInVzZXJuYW1lIjoidXNlciJ9.PXFU57PS94Da36MEVmnbSUIdo9UrJuRCP496Bipn8a0"

    data class MonthStepsResponse(
        val totalSteps: Int,
        val averageSteps: Double,
        val maxSteps: Int,
        val days: List<DaySteps>
    )

    data class DaySteps(
        val date: String,
        val steps: Int,
        val goal: Int
    )


    suspend fun getMonthSteps(month: Int, year: Int): MonthStepsResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.calendarApi.getMonthSteps(
                    month = month,
                    year = year,
                    authHeader = "Bearer $token"
                ).execute()

                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.e("CalendarApiService", "Error: ${response.code()} - ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("CalendarApiService", "Network error", e)
                null
            }
        }

}