package ru.itmo.se.mad.ui.main.measure

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.measurements.UpdateArmsRequest
import ru.itmo.se.mad.api.measurements.UpdateBodyFatRequest
import ru.itmo.se.mad.api.measurements.UpdateChestRequest
import ru.itmo.se.mad.api.measurements.UpdateHipsRequest
import ru.itmo.se.mad.api.measurements.UpdateMuscleMassRequest
import ru.itmo.se.mad.api.measurements.UpdateWaistRequest
import ru.itmo.se.mad.api.measurements.UpdateWeightRequest
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.main.main_screen.MenuItem
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray80



//@Preview
@Composable
fun MeasureWidget(onSelect: (content: @Composable () -> Unit) -> Unit, setTitle: (String) -> Unit) {
    val parameters = listOf(
        "Вес",
        "Талия",
        "Бёдра",
        "Грудь",
        "Руки",
        "Объём жира",
        "Мышечная масса",
    )

    val parameterValues = remember { mutableStateMapOf<String, String>() }
    val coroutineScope = rememberCoroutineScope()

    var selectedParam by remember { mutableStateOf<String?>(null) }

    val updateFunctions = mapOf<String, suspend (String) -> Unit>(
        "Вес" to { value ->
            ApiClient.measurementsApi.updateWeight(UpdateWeightRequest(value.toFloat()))
        },
        "Талия" to { value ->
            ApiClient.measurementsApi.updateWaist(UpdateWaistRequest(value.toFloat()))
        },
        "Бёдра" to { value ->
            ApiClient.measurementsApi.updateHips(UpdateHipsRequest(value.toFloat()))
        },
        "Грудь" to { value ->
            ApiClient.measurementsApi.updateChest(UpdateChestRequest(value.toFloat()))
        },
        "Руки" to { value ->
            ApiClient.measurementsApi.updateArms(UpdateArmsRequest(value.toFloat()))
        },
        "Объём жира" to { value ->
            ApiClient.measurementsApi.updateBodyFat(UpdateBodyFatRequest(value.toFloat()))
        },
        "Мышечная масса" to { value ->
            ApiClient.measurementsApi.updateMuscleMass(UpdateMuscleMassRequest(value.toFloat()))
        }
    )

    val labels = mapOf(
        "Вес" to "кг",
        "Талия" to "см",
        "Бёдра" to "см",
        "Грудь" to "см",
        "Руки" to "см",
        "Объём жира" to "%",
        "Мышечная масса" to "кг"
    )


    LaunchedEffect(Unit) {
        try {
            val goalResponse = ApiClient.measurementsApi.getMeasurements()
            parameterValues["Вес"] = goalResponse.weight.toString().orEmpty()
            parameterValues["Талия"] = goalResponse.waist.toString().orEmpty()
            parameterValues["Бёдра"] = goalResponse.hips.toString().orEmpty()
            parameterValues["Грудь"] = goalResponse.chest.toString().orEmpty()
            parameterValues["Руки"] = goalResponse.arms.toString().orEmpty()
            parameterValues["Объём жира"] = goalResponse.bodyFat.toString().orEmpty()
            parameterValues["Мышечная масса"] = goalResponse.muscleMass.toString().orEmpty()
        } catch (e: Exception) {
            Log.e("dbg", "Ошибка при загрузке измерений: ${e.localizedMessage}", e)
        }
    }

    LazyColumn(
        modifier = Modifier.padding(bottom = 6.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(parameters) { param ->
            MenuItem(
                title = param,
                subTitle = parameterValues[param].orEmpty(),
                onClick = {
                    val inputValueState = mutableStateOf(parameterValues[param] ?: "")
                    setTitle(param)
                    onSelect {
                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            LabeledTextField(
                                value = inputValueState.value,
                                onValueChange = { inputValueState.value = it },
                                placeholder = "Введите значение",
                                labelRight = labels[param] ?: ""
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                PrimaryButton(text = "Готово") {
                                    parameterValues[param] = inputValueState.value
                                    coroutineScope.launch {
                                        try {
                                            updateFunctions[param]?.invoke(inputValueState.value)
                                        } catch (e: Exception) {
                                            Log.e("dbg", "Ошибка при обновлении $param: ${e.localizedMessage}", e)
                                        }
                                    }
                                    setTitle("Что вы хотите добавить?")
                                    onSelect { AddItem(onSelect, setTitle) }
                                }
                                SecondaryButton(text = "Отмена") {
                                    setTitle("Что вы хотите добавить?")
                                    onSelect { AddItem(onSelect, setTitle) }
                                }
                            }
                        }
                    }
                }

            )
        }
    }
}



@Composable
fun ParameterElement(parameterName: String = "Вес") {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = parameterName,
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Normal,
        )
        Button(onClick = {}, shape = CircleShape,
            contentPadding = PaddingValues(0.dp),

            colors = ButtonColors(
            White, Black, White, White
        ), modifier = Modifier
            .size(32.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "", tint = WidgetGray80)
        }
    }
}