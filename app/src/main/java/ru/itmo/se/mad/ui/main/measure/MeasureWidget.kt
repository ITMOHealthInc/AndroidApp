package ru.itmo.se.mad.ui.main.measure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.model.MeasurementsViewModel
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.main.main_screen.MenuItem
import ru.itmo.se.mad.ui.main.products.AddItem


//@Preview
@Composable
fun MeasureWidget(
    measurementsViewModel: MeasurementsViewModel,
    onSelect: (content: @Composable () -> Unit) -> Unit,
    setTitle: (String) -> Unit
) {
    val parameters = listOf(
        "Вес",
        "Рост",
        "Талия",
        "Бёдра",
        "Грудь",
        "Руки",
        "Объём жира",
        "Мышечная масса",
    )

    val parameterValues = remember { mutableStateMapOf<String, String>() }

    val updateFunctions = mapOf<String, (String) -> Unit>(
        "Вес" to { value ->
            measurementsViewModel.weight = value.toFloat()
            measurementsViewModel.updateWeight()
        },
        "Рост" to { value ->
            measurementsViewModel.height = value.toFloat()
            measurementsViewModel.updateHeight()
        },
        "Талия" to { value ->
            measurementsViewModel.waist = value.toFloat()
            measurementsViewModel.updateWaist()
        },
        "Бёдра" to { value ->
            measurementsViewModel.hips = value.toFloat()
            measurementsViewModel.updateHips()
        },
        "Грудь" to { value ->
            measurementsViewModel.chest = value.toFloat()
            measurementsViewModel.updateChest()
        },
        "Руки" to { value ->
            measurementsViewModel.arms = value.toFloat()
            measurementsViewModel.updateArms()
        },
        "Объём жира" to { value ->
            measurementsViewModel.bodyFat = value.toFloat()
            measurementsViewModel.updateBodyFat()
        },
        "Мышечная масса" to { value ->
            measurementsViewModel.muscleMass = value.toFloat()
            measurementsViewModel.updateMuscleMass()
        }
    )

    val labels = mapOf(
        "Вес" to "кг",
        "Рост" to "см",
        "Талия" to "см",
        "Бёдра" to "см",
        "Грудь" to "см",
        "Руки" to "см",
        "Объём жира" to "%",
        "Мышечная масса" to "кг"
    )

    parameterValues["Вес"] = measurementsViewModel.weight.toString()
    parameterValues["Рост"] = measurementsViewModel.height.toString()
    parameterValues["Талия"] = measurementsViewModel.waist.toString()
    parameterValues["Бёдра"] = measurementsViewModel.hips.toString()
    parameterValues["Грудь"] = measurementsViewModel.chest.toString()
    parameterValues["Руки"] = measurementsViewModel.arms.toString()
    parameterValues["Объём жира"] = measurementsViewModel.bodyFat.toString()
    parameterValues["Мышечная масса"] = measurementsViewModel.muscleMass.toString()

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
                                    val func = updateFunctions[param];
                                    func?.invoke(inputValueState.value)
                                    setTitle("Что вы хотите добавить?")
                                    onSelect { AddItem(measurementsViewModel, onSelect, setTitle) }
                                }
                                SecondaryButton(text = "Отмена") {
                                    setTitle("Что вы хотите добавить?")
                                    onSelect { AddItem(measurementsViewModel, onSelect, setTitle) }
                                }
                            }
                        }
                    }
                }

            )
        }
    }
}