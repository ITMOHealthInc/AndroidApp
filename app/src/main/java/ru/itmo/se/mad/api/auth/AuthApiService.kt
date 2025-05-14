package ru.itmo.se.mad.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.itmo.se.mad.api.MessageResponse

interface AuthApiService {
    @POST("auth/registration")
    suspend fun register(@Body request: RegisterRequest) : Response<MessageResponse>
}