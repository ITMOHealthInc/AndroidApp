package ru.itmo.se.mad.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.auth.AuthApiService
import ru.itmo.se.mad.api.auth.LoginRequest
import ru.itmo.se.mad.api.auth.RegisterRequest
import ru.itmo.se.mad.storage.LocalStorage
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.alert.AlertType

class AuthViewModel(
    private val authApi: AuthApiService = ApiClient.authApi
) : ViewModel() {
    var name: String by mutableStateOf("")
    var login: String by mutableStateOf("")
    var password: String by mutableStateOf("")

    fun login(login: String = this.login, password: String = this.password, onSuccess: () -> Unit) {
        viewModelScope.launch {
            getAndSaveToken(login, password, onSuccess)
        }
    }

    fun register(
        login: String = this.login,
        password: String = this.password,
        name: String = this.name,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val regResponse = authApi.register(RegisterRequest(login, password, name))
            if (regResponse.isSuccessful) {
                getAndSaveToken(login, password, onSuccess)
            } else if (regResponse.code() == 409) {
                AlertManager.show("Пользователь уже зарегестрирован", AlertType.WARNING)
            } else {
                throw Exception("Strange response code on register: " + regResponse.code())
            }
        }
    }

    private suspend fun getAndSaveToken(login: String, password: String, onSuccess: () -> Unit) {
        val token = getToken(login, password)
        token?.let {
            LocalStorage.saveToken(it)
            onSuccess()
        }
    }

    private suspend fun getToken(login: String, password: String) : String? {
        val response = authApi.login(LoginRequest(login, password))
        if (response.isSuccessful) {
            return response.body()!!.token
        } else if (response.code() == 401) {
            AlertManager.warn("Неверный пароль")
            return null
        } else {
            throw Exception("Strange response code on login: " + response.code())
        }
    }
}

