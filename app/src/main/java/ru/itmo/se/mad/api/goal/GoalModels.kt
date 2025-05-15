package ru.itmo.se.mad.api.goal

data class CreateGoalRequest(
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Int,
    val steps_goal: Int,
    val bju_goal: String
)

data class UserGoal(
    val user_id: String,
    val goal_type: String,
    val activity_level: String,
    val weekly_target: Double,
    val calorie_goal: Int,
    val water_goal: Int,
    val steps_goal: Int,
    val bju_goal: String
)

data class GoalCreatedResponse(val message: String, val goal: UserGoal)