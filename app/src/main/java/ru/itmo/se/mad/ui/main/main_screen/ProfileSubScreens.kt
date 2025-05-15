package ru.itmo.se.mad.ui.main.main_screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.model.ActivityLevel
import ru.itmo.se.mad.model.Goal
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.model.ProfileViewModel
import ru.itmo.se.mad.model.safeToDouble
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.layout.EditableTextRow
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PhotoPicker
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption
import ru.itmo.se.mad.ui.theme.SFProDisplay

@Composable
fun AccountScreen(
    profileViewModel: ProfileViewModel
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        profileViewModel.profilePictureUrl = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoPicker(
            imageUri = profileViewModel.profilePictureUrl,
            onPickImage = {
                launcher.launch("image/*")
                // TODO: обновление на бэке
            }
        )
        Spacer(Modifier.height(16.dp))
        EditableTextRow(
            initialText = profileViewModel.name,
            onTextChange = { newText ->
                profileViewModel.updateName(newText)
            }
        )

    }
}

@Composable
fun GoalsScreen(
    storage: OnboardingViewModel,
    popupNavController: NavHostController
) {
    var itemChangeDialogShown by remember { mutableStateOf(false) }
    var currentDialogType by remember { mutableStateOf<String?>(null) } // тип диалога

    val goalOptions = listOf(Goal.WEIGHT_LOSS, Goal.WEIGHT_MAINTENANCE, Goal.WEIGHT_GAIN)
    val selected = storage.goal
    var goalSelectedBefore by remember { mutableStateOf(Goal.NOT_SELECTED) }
    var newWeightGoal by remember { mutableStateOf("") }

    var newCaloriesGoal by remember { mutableStateOf("") }
    var newWaterGoal by remember { mutableStateOf("") }
    var newStepsGoal by remember { mutableStateOf("") }



    val activityOptions = listOf(ActivityLevel.HIGH, ActivityLevel.MEDIUM, ActivityLevel.LOW)
    val selectedActivity = storage.activity
    var activitySelectedBefore by remember { mutableStateOf(ActivityLevel.NOT_SELECTED) }

    val isGoalValid = when (storage.goal) {
        Goal.WEIGHT_LOSS -> newWeightGoal != "" && newWeightGoal.safeToDouble() < storage.weight.safeToDouble()
        Goal.WEIGHT_MAINTENANCE -> newWeightGoal != "" && kotlin.math.abs(newWeightGoal.safeToDouble() - storage.weight.safeToDouble()) <= 1.0
        Goal.WEIGHT_GAIN -> newWeightGoal != "" && newWeightGoal.safeToDouble() > storage.weight.safeToDouble()
        else -> false
    }

    var caloriesGoal by remember { mutableStateOf("") }
    var waterGoal by remember { mutableStateOf("") }
    var stepsGoal by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        try {
            val goalResponse = ApiClient.goalApi.getGoal()
            caloriesGoal = goalResponse.calorie_goal.toString()
            waterGoal = goalResponse.water_goal.toString()
            stepsGoal = goalResponse.steps_goal.toString()


        } catch (e: Exception) {
            Log.e("dbg", "Ошибка при загрузке: ${e.localizedMessage}", e)
            AlertManager.error("Ошибка при загрузке")
        }
    }

    if (itemChangeDialogShown) {
        Popup(
            isVisible = true,
            onDismissRequest = {
                itemChangeDialogShown = false
                currentDialogType = null
            },
            title = when (currentDialogType) {
                "goal" -> "Новая цель"
                "newWeightInput" -> "Новая цель веса"
                "newCaloriesInput" -> "Новая цель калорий"
                "newWaterInput" -> "Новая цель воды"
                "newStepsInput" -> "Новая цель шагов"
                "activity" -> "Уровень активности"
                "weekly" -> "Еженедельная цель"
                else -> ""
            }
        ) {
            when (currentDialogType) {
                "goal" -> {
                    Column {
                        goalOptions.forEach {
                            SelectableOption(
                                text = it.displayName,
                                selected = selected == it,
                                onClick = { storage.goal = it }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Изменение цели приведёт к перерасчёту норм потребления",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = SFProDisplay,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(Modifier.height(32.dp))
                        if (goalSelectedBefore.toString() != selected.toString()) {
                            if(selected != Goal.WEIGHT_MAINTENANCE) {
                                PrimaryButton(text = "Далее", onClick = {
                                    currentDialogType = "newWeightInput"
                                })
                            } else {
                                PrimaryButton(text = "Готово", onClick = {
                                    itemChangeDialogShown = false
                                    currentDialogType = "loading"
                                    if (storage.updateGoal(selected, "")) currentDialogType = null
                                })
                            }
                        } else {
                            SecondaryButton(text = "Закрыть", onClick = {
                                itemChangeDialogShown = false
                            })
                        }
                    }
                }
                "newWeightInput" -> {
                    LabeledTextField(
                        value = newWeightGoal,
                        onValueChange = { newWeightGoal = it },
                        placeholder = "Вес",
                        labelRight = "кг"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: " + storage.goalWeight.safeToDouble() + " кг",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (isGoalValid) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            goalSelectedBefore = storage.goal
                            if (storage.updateGoal(selected, newWeightGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Назад", onClick = {currentDialogType = "goal"})
                    }
                }
                "newCaloriesInput" -> {
                    LabeledTextField(
                        value = newCaloriesGoal,
                        onValueChange = { newCaloriesGoal = it },
                        placeholder = "Калории",
                        labelRight = "ккал"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $caloriesGoal ккал",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newCaloriesGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateCaloriesGoal(newCaloriesGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = {currentDialogType = null })
                    }
                }
                "newWaterInput" -> {
                    LabeledTextField(
                        value = newWaterGoal,
                        onValueChange = { newWaterGoal = it },
                        placeholder = "Вода",
                        labelRight = "л"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $waterGoal л",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newWaterGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateWaterGoal(newWaterGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = {currentDialogType = null })
                    }
                }
                "newStepsInput" -> {
                    LabeledTextField(
                        value = newStepsGoal,
                        onValueChange = { newStepsGoal = it },
                        placeholder = "Шаги",
                        labelRight = ""
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $stepsGoal шагов",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newStepsGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateStepsGoal(newStepsGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = {currentDialogType = null })
                    }
                }
                "loading" -> {
                    Text(
                        text = "Обновление",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
                "activity" -> {
                    Column {
                        activityOptions.forEach {
                            SelectableOption(
                                text = it.displayName,
                                selected = selectedActivity == it,
                                onClick = { storage.activity = it }
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Изменение уровня активности приведёт к перерасчёту норм потребления",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = SFProDisplay,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(Modifier.height(32.dp))
                        if (activitySelectedBefore.toString() != selectedActivity.toString()) {
                            PrimaryButton(text = "Готово", onClick = {
                                itemChangeDialogShown = false
                                currentDialogType = "loading"
                                if (storage.updateGoal(selected, "")) currentDialogType = null
                            })
                        } else {
                            SecondaryButton(text = "Закрыть", onClick = {
                                itemChangeDialogShown = false
                            })
                        }
                    }
                }
                "weekly" -> {
                    PrimaryButton(text = "Готово", onClick = {
                        itemChangeDialogShown = false
                        currentDialogType = null
                    })
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileMenuItem(
            title = "Цель",
            subTitle = storage.goal.toString(),
            onClick = {
                goalSelectedBefore = selected
                currentDialogType = "goal"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Уровень активности",
            subTitle = storage.activity.toString(),
            onClick = {
                currentDialogType = "activity"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Цель калорий",
            subTitle = caloriesGoal,
            onClick = {
                currentDialogType = "newCaloriesInput"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Цели БЖУ",
            onClick = {
                popupNavController.navigate("macro")
            }
        )
        ProfileMenuItem(
            title = "Цель воды",
            subTitle = waterGoal,
            onClick = {
                currentDialogType = "newWaterInput"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Цель шагов",
            subTitle = stepsGoal,
            onClick = {
                currentDialogType = "newStepsInput"
                itemChangeDialogShown = true
            }
        )
    }
}

@Composable
fun MacroGoalsScreen(
    storage: OnboardingViewModel,
) {
    var itemChangeDialogShown by remember { mutableStateOf(false) }
    var currentDialogType by remember { mutableStateOf<String?>(null) }



    var proteinsGoal by remember { mutableStateOf("") }
    var fatsGoal by remember { mutableStateOf("") }
    var carbohydratesGoal by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val goalResponse = ApiClient.goalApi.getGoal()
            proteinsGoal = goalResponse.proteins_goal.toString()
            fatsGoal = goalResponse.fats_goal.toString()
            carbohydratesGoal = goalResponse.carbohydrates_goal.toString()

        } catch (e: Exception) {
            Log.e("dbg", "Ошибка при загрузке: ${e.localizedMessage}", e)
            AlertManager.error("Ошибка при загрузке")
        }
    }

    var newPGoal by remember { mutableStateOf(proteinsGoal) }
    var newFGoal by remember { mutableStateOf(fatsGoal) }
    var newCGoal by remember { mutableStateOf(carbohydratesGoal) }

    if (itemChangeDialogShown) {
        Popup(
            isVisible = true,
            onDismissRequest = {
                itemChangeDialogShown = false
                currentDialogType = null
            },
            title = when (currentDialogType) {
                "newPInput" -> "Новая цель белков"
                "newFInput" -> "Новая цель жиров"
                "newCInput" -> "Новая цель углеводов"
                else -> ""
            }
        ) {
            when (currentDialogType) {
                "newPInput" -> {
                    LabeledTextField(
                        value = newPGoal,
                        onValueChange = { newPGoal = it },
                        placeholder = "Белки",
                        labelRight = "г"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $proteinsGoal г",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newPGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateMacro(newPGoal, newFGoal, newCGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = { itemChangeDialogShown = false })
                    }
                }
                "newFInput" -> {
                    LabeledTextField(
                        value = newFGoal,
                        onValueChange = { newFGoal = it },
                        placeholder = "Жиры",
                        labelRight = "г"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $fatsGoal г",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newFGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateMacro(newPGoal, newFGoal, newCGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = { itemChangeDialogShown = false })
                    }
                }
                "newCInput" -> {
                    LabeledTextField(
                        value = newCGoal,
                        onValueChange = { newCGoal = it },
                        placeholder = "Углеводы",
                        labelRight = "г"
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Ваша текущая цель: $carbohydratesGoal г",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(Modifier.height(32.dp))
                    if (newCGoal.safeToDouble() > 0) {
                        PrimaryButton(text = "Готово", onClick = {
                            itemChangeDialogShown = false
                            currentDialogType = "loading"
                            if (storage.updateMacro(newPGoal, newFGoal, newCGoal)) currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Отмена", onClick = { itemChangeDialogShown = false })
                    }
                }
                "loading" -> {
                    Text(
                        text = "Обновление",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = SFProDisplay,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 32.dp)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileMenuItem(
            title = "Белки",
            subTitle = proteinsGoal,
            onClick = {
                currentDialogType = "newPInput"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Жиры",
            subTitle = fatsGoal,
            onClick = {
                currentDialogType = "newFInput"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Углеводы",
            subTitle = carbohydratesGoal,
            onClick = {
                currentDialogType = "newCInput"
                itemChangeDialogShown = true
            }
        )
    }
}


