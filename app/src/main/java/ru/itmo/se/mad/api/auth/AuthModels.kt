package ru.itmo.se.mad.api.auth

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val username: String, val password: String, val name : String)

data class TokenResponse(val token: String)
data class ProfileResponse(val username : String, val name : String, val profilePictureUrl : String)