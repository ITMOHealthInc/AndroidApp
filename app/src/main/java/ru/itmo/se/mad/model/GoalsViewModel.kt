package ru.itmo.se.mad.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.goal.CreateUserGoalRequest
import ru.itmo.se.mad.api.goal.UserGoalDto

class GoalsViewModel() : ViewModel() {
    var type: Goal by mutableStateOf(Goal.NOT_SELECTED)
    var activity: ActivityLevel by mutableStateOf(ActivityLevel.NOT_SELECTED)
    var calories: Int by mutableIntStateOf(2800)
    var water: Int by mutableIntStateOf(3000)
    var steps: Int by mutableIntStateOf(10000)
    var proteins: Int by mutableIntStateOf(90)
    var fats: Int by mutableIntStateOf(65)
    var carbohydrates: Int by mutableIntStateOf(250)

    // TODO implement
    var weightGoal: Float by mutableFloatStateOf(0f)

    fun load() {
        viewModelScope.launch {
            val dto = ApiClient.goalApi.getGoal()
            fromDto(dto)
        }
    }

    fun updateGoal() {
        viewModelScope.launch {
            ApiClient.goalApi.updateGoal(CreateUserGoalRequest(type.name, activity.name))
            val dto = ApiClient.goalApi.getGoal()
            fromDto(dto)
        }
    }

    private fun fromDto(dto: UserGoalDto) {
        this.type = dto.goal_type
        this.activity = dto.activity_level
        this.calories = dto.calorie_goal
        this.water = dto.water_goal
        this.steps = dto.steps_goal
        this.proteins = dto.proteins_goal
        this.fats = dto.fats_goal
        this.carbohydrates = dto.carbohydrates_goal
    }

}