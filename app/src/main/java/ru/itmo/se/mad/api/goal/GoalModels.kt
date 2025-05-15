package ru.itmo.se.mad.api.goal

import ru.itmo.se.mad.model.ActivityLevel
import ru.itmo.se.mad.model.Goal

data class CreateUserGoalRequest(
    val goal_type: String,
    val activity_level: String,
    val weight_goal: Double
)

data class UserGoalDto(
    val user_id: String? = null,
    val goal_type: Goal,
    val activity_level: ActivityLevel,
    val calorie_goal: Int,
    val water_goal: Int,
    val steps_goal: Int,
    val proteins_goal: Int,
    val fats_goal: Int,
    val carbohydrates_goal: Int,
    val weight_goal: Double
)