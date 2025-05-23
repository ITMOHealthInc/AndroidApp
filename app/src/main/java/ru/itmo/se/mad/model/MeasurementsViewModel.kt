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
import ru.itmo.se.mad.api.measurements.MeasurementResponse
import ru.itmo.se.mad.api.measurements.UpdateArmsRequest
import ru.itmo.se.mad.api.measurements.UpdateBodyFatRequest
import ru.itmo.se.mad.api.measurements.UpdateChestRequest
import ru.itmo.se.mad.api.measurements.UpdateHeightRequest
import ru.itmo.se.mad.api.measurements.UpdateHipsRequest
import ru.itmo.se.mad.api.measurements.UpdateMuscleMassRequest
import ru.itmo.se.mad.api.measurements.UpdateWaistRequest
import ru.itmo.se.mad.api.measurements.UpdateWeightRequest

class MeasurementsViewModel() : ViewModel() {
    var gender: Gender by mutableStateOf(Gender.NOT_SELECTED)
    var weight: Float by mutableFloatStateOf(0f)
    var height: Float by mutableFloatStateOf(0f)
    var waist: Float by mutableFloatStateOf(0f)
    var hips: Float by mutableFloatStateOf(0f)
    var chest: Float by mutableFloatStateOf(0f)
    var arms: Float by mutableFloatStateOf(0f)
    var bodyFat: Float by mutableFloatStateOf(0f)
    var muscleMass: Float by mutableFloatStateOf(0f)
    var bloodGlucose: Float by mutableFloatStateOf(0f)
    var bloodPressureSystolic: Int by mutableIntStateOf(0)
    var bloodPressureDiastolic: Int by mutableIntStateOf(0)
    var measuredAt: String by mutableStateOf("")

    fun load() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.getMeasurements()
            fromDto(dto)
        }
    }

    fun updateWeight() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateWeight(UpdateWeightRequest(weight))
            fromDto(dto)
        }
    }

    fun updateHeight() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateHeight(UpdateHeightRequest(height))
            fromDto(dto)
        }
    }

    fun updateWaist() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateWaist(UpdateWaistRequest(waist))
            fromDto(dto)
        }
    }

    fun updateHips() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateHips(UpdateHipsRequest(hips))
            fromDto(dto)
        }
    }

    fun updateChest() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateChest(UpdateChestRequest(chest))
            fromDto(dto)
        }
    }

    fun updateArms() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateArms(UpdateArmsRequest(arms))
            fromDto(dto)
        }
    }

    fun updateBodyFat() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateBodyFat(UpdateBodyFatRequest(bodyFat))
            fromDto(dto)
        }
    }

    fun updateMuscleMass() {
        viewModelScope.launch {
            val dto = ApiClient.measurementsApi.updateMuscleMass(UpdateMuscleMassRequest(muscleMass))
            fromDto(dto)
        }
    }

    private fun fromDto(dto: MeasurementResponse) {
        this.gender = Gender.valueOf(dto.gender)
        this.weight = dto.weight
        this.height = dto.height
        this.waist = dto.waist
        this.hips = dto.hips
        this.chest = dto.chest
        this.arms = dto.arms
        this.bodyFat = dto.bodyFat
        this.muscleMass = dto.muscleMass
        this.bloodGlucose = dto.bloodGlucose
        this.bloodPressureSystolic = dto.bloodPressureSystolic
        this.bloodPressureDiastolic = dto.bloodPressureDiastolic
        this.measuredAt = dto.measuredAt
    }
}