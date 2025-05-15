package ru.itmo.se.mad.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

enum class Gender(val displayName: String) {
    MALE("Мужской"),
    FEMALE("Женский"),
    NOT_SELECTED("Не указан");

    override fun toString(): String = displayName
}

enum class Goal(val displayName: String) {
    LOSE("Снижение веса"),
    GAIN("Набор веса"),
    MAINTAIN("Поддержание веса"),
    NOT_SELECTED("Не выбрано");

    override fun toString(): String = displayName
}

class OnboardingViewModel : ViewModel() {
    var photoUri: Uri? by mutableStateOf(null)
    var goal: Goal by mutableStateOf(Goal.NOT_SELECTED)
    var height: String by mutableStateOf("")
    var weight: String by mutableStateOf("")
    var goalWeight: String by mutableStateOf("")
    var gender: Gender by mutableStateOf(Gender.NOT_SELECTED)

    fun updateGoal(
        goal: Goal,
        newWeightGoal: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
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
}

fun String.safeToDouble(): Double {
    return this.toDoubleOrNull() ?: 0.0
}



