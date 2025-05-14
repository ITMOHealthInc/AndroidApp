package ru.itmo.se.mad.api.auth

data class RegisterRequest(
    val username: String,
    val password: String,
    val name: String
)