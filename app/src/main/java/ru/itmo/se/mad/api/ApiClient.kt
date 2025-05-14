package ru.itmo.se.mad.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itmo.se.mad.api.auth.AuthApiService
import ru.itmo.se.mad.api.products.ProductsApiService
import ru.itmo.se.mad.storage.LocalStorage
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.main.main_screen.calendar.CalendarRepository
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .addInterceptor(AuthorizationInterceptor())
        .addInterceptor(ServerErrorInterceptor())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val productsApi: ProductsApiService by lazy {
        retrofit.create(ProductsApiService::class.java)
    }
    val calendarApi: CalendarRepository by lazy {
        retrofit.create(CalendarRepository::class.java)
    }
}

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origRequest = chain.request()

        val requestBuilder = origRequest.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")

        val token = LocalStorage.getToken()
        token?.let {
            requestBuilder.header("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        if (response.code == 401) {
            LocalStorage.clearToken()
        }

        return response
    }
}

class ServerErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.isSuccessful) {
            return response
        }

        if (response.code in 500..599) {
            AlertManager.error("Сервер не отвечает")
        }

        return response
    }
}