package ru.itmo.se.mad.api.measurements

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MeasurementsApiService {
    @GET("user_measurements/measurements")
    suspend fun getMeasurements() : MeasurementResponse

    @POST("user_measurements/update-weight")
    suspend fun updateWeight(@Body request: UpdateWeightRequest) : MeasurementResponse

    @POST("user_measurements/update-waist")
    suspend fun updateWaist(@Body request: UpdateWaistRequest) : MeasurementResponse

    @POST("user_measurements/update-hips")
    suspend fun updateHips(@Body request: UpdateHipsRequest) : MeasurementResponse

    @POST("user_measurements/update-chest")
    suspend fun updateChest(@Body request: UpdateChestRequest) : MeasurementResponse

    @POST("user_measurements/update-body-fat")
    suspend fun updateBodyFat(@Body request: UpdateBodyFatRequest) : MeasurementResponse

    @POST("user_measurements/update-muscle-mass")
    suspend fun updateMuscleMass(@Body request: UpdateMuscleMassRequest) : MeasurementResponse

    @POST("user_measurements/update-blood-glucose")
    suspend fun updateBloodGlucose(@Body request: UpdateBloodGlucoseRequest) : MeasurementResponse

    @POST("user_measurements/update-blood-pressure-systolic")
    suspend fun updateBloodPressureSystolic(@Body request: UpdateBloodPressureSystolicRequest) : MeasurementResponse

    @POST("user_measurements/update-blood-pressure-diastolic")
    suspend fun updateBloodPressureDiastolic(@Body request: UpdateBloodPressureDiastolicRequest) : MeasurementResponse

    @POST("user_measurements/update-gender")
    suspend fun updateGender(@Body request: UpdateGenderRequest) : MeasurementResponse

    @POST("user_measurements/update-height")
    suspend fun updateHeight(@Body request: UpdateHeightRequest) : MeasurementResponse

    @POST("user_measurements/update-arms")
    suspend fun updateArms(@Body request: UpdateArmsRequest) : MeasurementResponse
}