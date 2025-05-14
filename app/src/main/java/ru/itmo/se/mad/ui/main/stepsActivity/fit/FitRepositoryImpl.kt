package ru.itmo.se.mad.ui.main.stepsActivity.fit

import retrofit2.Call
import ru.itmo.se.mad.ui.main.stepsActivity.fit.FitApiService


class FitRepositoryImpl(private val fitApiService: FitApiService) : FitRepository {
    override fun getSteps(): Call<FitApiService.StepsResponse> {
        return fitApiService.activityApi.getSteps()
    }

    override fun setDailyGoal(goal: FitApiService.GoalRequest): Call<Void> {
        return fitApiService.activityApi.setDailyGoal(goal)
    }

    override fun setSteps(stepsData: FitApiService.StepsRequest): Call<Void> {
        return fitApiService.activityApi.setSteps(stepsData)
    }
}