package ru.itmo.se.mad.storage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.auth.AuthApiService
import ru.itmo.se.mad.api.auth.LoginRequest

class OauthViewModel(
    private val authApi: AuthApiService = ApiClient.authApi
) : ViewModel() {
    var name: String by mutableStateOf("")
    var login: String by mutableStateOf("")
    var password: String by mutableStateOf("")

    fun login(login: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = authApi.login(LoginRequest(login, password))
            println(response.body())
        }
        onSuccess()
    }

    fun register(name: String, login: String, password: String, onSuccess: () -> Unit) {
        // TODO: сделать запрос к бэку
        println("Регистрация: $login, Пароль: $password")
        onSuccess()
    }

}

