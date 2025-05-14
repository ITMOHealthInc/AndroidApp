package ru.itmo.se.mad.storage

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Gender(val displayName: String) {
    MALE("Мужской"),
    FEMALE("Женский"),
    NOT_SELECTED("Не указан");

    override fun toString(): String = displayName
}

enum class Goal(val displayName: String) {
    LOSE("Сбросить вес"),
    GAIN("Набрать вес"),
    MAINTAIN("Поддержать вес"),
    NOT_SELECTED("Не выбрано");

    override fun toString(): String = displayName
}

class OnboardingViewModel : ViewModel() {
    var photoUri: Uri? by mutableStateOf(null)
    var goal: Goal by mutableStateOf(Goal.NOT_SELECTED)
    var height: String by mutableStateOf("")
    var weight: String by mutableStateOf("")
    var gender: Gender by mutableStateOf(Gender.NOT_SELECTED)
}

    fun complete(
        photoUri: Uri,
        goal: String,
        height: String,
        weight: String,
        gender: Gender,
        onSuccess: () -> Unit
    ) {
        // TODO: сделать запрос к бэку
        onSuccess()
    }

