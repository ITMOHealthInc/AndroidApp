package ru.itmo.se.mad.api.goal

data class CreateGoalRequest(
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Double,
    val steps_goal: Int,
    val proteins_goal: Int,
    val fats_goal: Int,
    val carbohydrates_goal: Int,
)

data class UserGoal(
    val user_id: String,
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Double,
    val steps_goal: Int,
    val proteins_goal: Int,
    val fats_goal: Int,
    val carbohydrates_goal: Int,
)

data class GoalCreatedResponse(val message: String, val goal: UserGoal)