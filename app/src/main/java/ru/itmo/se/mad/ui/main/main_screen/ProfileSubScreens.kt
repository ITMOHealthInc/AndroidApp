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
import ru.itmo.se.mad.model.GoalsViewModel
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
    goalsViewModel: GoalsViewModel,
    storage: OnboardingViewModel,
    popupNavController: NavHostController
) {
    var itemChangeDialogShown by remember { mutableStateOf(false) }
    var currentDialogType by remember { mutableStateOf<String?>(null) } // тип диалога

    val goalOptions = listOf(Goal.WEIGHT_LOSS, Goal.WEIGHT_MAINTENANCE, Goal.WEIGHT_GAIN)
    var goalSelectedBefore by remember { mutableStateOf(Goal.NOT_SELECTED) }
    var newWeightGoal by remember { mutableStateOf("") }

    val activityOptions = listOf(ActivityLevel.HIGH, ActivityLevel.MEDIUM, ActivityLevel.LOW)
    val selectedActivity = goalsViewModel.activity
    var activitySelectedBefore by remember { mutableStateOf(ActivityLevel.NOT_SELECTED) }

    val isGoalValid = when (goalsViewModel.type) {
        Goal.WEIGHT_LOSS -> newWeightGoal != "" && newWeightGoal.safeToDouble() < storage.weight.safeToDouble()
        Goal.WEIGHT_MAINTENANCE -> newWeightGoal != "" && kotlin.math.abs(newWeightGoal.safeToDouble() - storage.weight.safeToDouble()) <= 1.0
        Goal.WEIGHT_GAIN -> newWeightGoal != "" && newWeightGoal.safeToDouble() > storage.weight.safeToDouble()
        else -> false
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
                "activity" -> "Уровень активности"
                "newWeightInput" -> "Новая цель веса"
                else -> ""
            }
        ) {
            when (currentDialogType) {
                "goal" -> {
                    Column {
                        goalOptions.forEach {
                            SelectableOption(
                                text = it.displayName,
                                selected = goalsViewModel.type == it,
                                onClick = { goalsViewModel.type = it }
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
                        if (goalSelectedBefore != goalsViewModel.type) {
                            if (goalsViewModel.type != Goal.WEIGHT_MAINTENANCE) {
                                PrimaryButton(text = "Далее", onClick = {
                                    currentDialogType = "newWeightInput"
                                })
                            } else {
                                PrimaryButton(text = "Готово", onClick = {
                                    itemChangeDialogShown = false
                                    currentDialogType = "loading"
                                    goalsViewModel.updateGoal()
                                    currentDialogType = null
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
                            goalSelectedBefore = goalsViewModel.type
                            goalsViewModel.updateGoal()
                            currentDialogType = null
                        })
                    } else {
                        SecondaryButton(text = "Назад", onClick = {currentDialogType = "goal"})
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
                                onClick = { goalsViewModel.activity = it }
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
                                goalsViewModel.updateGoal()
                                currentDialogType = null
                            })
                        } else {
                            SecondaryButton(text = "Закрыть", onClick = {
                                itemChangeDialogShown = false
                            })
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MenuItem(
            title = "Цель",
            subTitle = goalsViewModel.type.toString(),
            onClick = {
                goalSelectedBefore = goalsViewModel.type
                currentDialogType = "goal"
                itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Уровень активности",
            subTitle = goalsViewModel.activity.toString(),
            onClick = {
                currentDialogType = "activity"
                itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Цель калорий",
            subTitle = goalsViewModel.calories.toString(),
            onClick = {
                //currentDialogType = "newCaloriesInput"
                //itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Цели БЖУ",
            onClick = {
                popupNavController.navigate("macro")
            }
        )
        MenuItem(
            title = "Цель воды",
            subTitle = goalsViewModel.water.toString(),
            onClick = {
                //currentDialogType = "newWaterInput"
                //itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Цель шагов",
            subTitle = goalsViewModel.steps.toString(),
            onClick = {
                //currentDialogType = "newStepsInput"
                //itemChangeDialogShown = true
            }
        )
    }
}

@Composable
fun MacroGoalsScreen(
    goalsViewModel: GoalsViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MenuItem(
            title = "Белки",
            subTitle = goalsViewModel.proteins.toString(),
            onClick = {
                //currentDialogType = "newPInput"
                //itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Жиры",
            subTitle = goalsViewModel.fats.toString(),
            onClick = {
                //currentDialogType = "newFInput"
                //itemChangeDialogShown = true
            }
        )
        MenuItem(
            title = "Углеводы",
            subTitle = goalsViewModel.carbohydrates.toString(),
            onClick = {
                //currentDialogType = "newCInput"
                //itemChangeDialogShown = true
            }
        )
    }
}


