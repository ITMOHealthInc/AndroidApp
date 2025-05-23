package ru.itmo.se.mad.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.itmo.se.mad.api.MessageResponse

interface AuthApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest) : Response<MessageResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest) : Response<TokenResponse>

    @GET("auth/profile")
    suspend fun getProfile() : ProfileResponse

    @PUT("auth/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest) : MessageResponse
}