package ru.itmo.se.mad.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.goal.CreateUserGoalRequest
import ru.itmo.se.mad.api.measurements.UpdateGenderRequest
import ru.itmo.se.mad.api.measurements.UpdateHeightRequest
import ru.itmo.se.mad.api.measurements.UpdateWeightRequest

enum class Gender(val displayName: String) {
    MALE("Мужской"),
    FEMALE("Женский"),
    NOT_SELECTED("Не указан");

    override fun toString(): String = displayName
}

enum class ActivityLevel(val displayName: String) {
    HIGH("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий"),
    NOT_SELECTED("Не указан");

    override fun toString(): String = displayName
}

enum class Goal(val displayName: String) {
    WEIGHT_LOSS("Снижение веса"),
    WEIGHT_GAIN("Набор веса"),
    WEIGHT_MAINTENANCE("Поддержание веса"),
    NOT_SELECTED("Не выбрано");

    override fun toString(): String = displayName
}

class OnboardingViewModel : ViewModel() {
    var photoUri: Uri? by mutableStateOf(null)

    var goal: Goal by mutableStateOf(Goal.NOT_SELECTED)
    var activity: ActivityLevel by mutableStateOf(ActivityLevel.NOT_SELECTED)

    var height: String by mutableStateOf("")
    var weight: String by mutableStateOf("")

    var gender: Gender by mutableStateOf(Gender.NOT_SELECTED)

    // Useles. To remove
    var goalWeight: String by mutableStateOf("")
    var calorieGoal: String by mutableStateOf("")
    var weeklyGoal: String by mutableStateOf("")
    var proteinGoal: String by mutableStateOf("")
    var fatGoal: String by mutableStateOf("")
    var carbohydrateGoal: String by mutableStateOf("")
    var waterGoal: String by mutableStateOf("")
    var stepsGoal: String by mutableStateOf("")

    fun saveGoals() {
        viewModelScope.launch {
            ApiClient.goalApi.createGoal(CreateUserGoalRequest(goal.name, activity.name))
        }
    }

    fun saveMeasurements() {
        viewModelScope.launch {
            ApiClient.measurementsApi.updateHeight(UpdateHeightRequest(height.toFloat()))
            ApiClient.measurementsApi.updateWeight(UpdateWeightRequest(weight.toFloat()))
        }
    }

    fun saveSex() {
        viewModelScope.launch {
            ApiClient.measurementsApi.updateGender(UpdateGenderRequest(gender.name))
        }
    }

    fun updateUserName(
        name: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateUserProfileImage(
        profilePictureUri: Uri?,
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateGoal(
        goal: Goal,
        newWeightGoal: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateCaloriesGoal(
        newCaloriesGoal: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateWaterGoal(
        newWaterGoal: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateStepsGoal(
        newStepsGoal: String
    ): Boolean {
        // TODO: сделать запрос к бэку
        return true
    }

    fun updateMacro(
        protein: String,
        fat: String,
        carbohydrate: String,
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



