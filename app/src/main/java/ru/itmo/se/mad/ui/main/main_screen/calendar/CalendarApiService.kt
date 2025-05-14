package ru.itmo.se.mad.ui.main.main_screen.calendar

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itmo.se.mad.ui.main.stepsActivity.fit.FitRepository


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

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(original.method, original.body)

            chain.proceed(requestBuilder.build())
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val calendarApi = retrofit.create(CalendarRepository::class.java)

    suspend fun getMonthSteps(month: Int, year: Int): MonthStepsResponse? =
        withContext(Dispatchers.IO) {
            try {
                val response = calendarApi.getMonthSteps(
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