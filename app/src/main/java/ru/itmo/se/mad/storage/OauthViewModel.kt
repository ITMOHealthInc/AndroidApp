package ru.itmo.se.mad.storage

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OauthViewModel : ViewModel() {
    var login: String by mutableStateOf("")
    var password: String by mutableStateOf("")

    fun login(login: String, password: String, onSuccess: () -> Unit) {
        // TODO: сделать запрос к бэку
        println("Логин: $login, Пароль: $password")
        onSuccess()
    }

    fun register(login: String, password: String, onSuccess: () -> Unit) {
        // TODO: сделать запрос к бэку
        println("Регистрация: $login, Пароль: $password")
        onSuccess()
    }

}

