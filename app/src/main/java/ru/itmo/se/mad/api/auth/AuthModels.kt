package ru.itmo.se.mad.api.auth

data class AuthRequest(val username: String, val password: String)
data class TokenResponse(val token: String)