package ru.itmo.se.mad.ui.main.main_screen

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
import ru.itmo.se.mad.model.Goal
import ru.itmo.se.mad.model.AuthViewModel
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.model.safeToDouble
import ru.itmo.se.mad.ui.layout.EditableTextRow
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PhotoPicker
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.theme.SFProDisplay

@Composable
fun AccountScreen(
    storage: OnboardingViewModel,
    oauthStorage: AuthViewModel
) {


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        storage.photoUri = uri
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoPicker(
            imageUri = storage.photoUri,
            onPickImage = { launcher.launch("image/*") }
        )
        Spacer(Modifier.height(16.dp))
        EditableTextRow(
            initialText = oauthStorage.name,
            onTextChange = { newText ->
                println("Изменено на: $newText")
            }
        )

    }
}

@Composable
fun GoalsScreen(
    storage: OnboardingViewModel,
    oauthStorage: AuthViewModel
) {
    var itemChangeDialogShown by remember { mutableStateOf(false) }
    var currentDialogType by remember { mutableStateOf<String?>(null) } // тип диалога

    val goalOptions = listOf(Goal.LOSE, Goal.MAINTAIN, Goal.GAIN)
    val selected = storage.goal
    var selectedBefore by remember { mutableStateOf(Goal.NOT_SELECTED) }
    var newWeightGoal by remember { mutableStateOf("") }

    val isGoalValid = when (storage.goal) {
        Goal.LOSE -> newWeightGoal != "" && newWeightGoal.safeToDouble() < storage.weight.safeToDouble()
        Goal.MAINTAIN -> newWeightGoal != "" && kotlin.math.abs(newWeightGoal.safeToDouble() - storage.weight.safeToDouble()) <= 1.0
        Goal.GAIN -> newWeightGoal != "" && newWeightGoal.safeToDouble() > storage.weight.safeToDouble()
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
                "newWeightInput" -> "Новая цель веса"
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
                        if (selectedBefore.toString() != selected.toString()) {
                            if(selected != Goal.MAINTAIN) {
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
                            selectedBefore = storage.goal
                            if (storage.updateGoal(selected, newWeightGoal)) currentDialogType = null
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
                    PrimaryButton(text = "Готово", onClick = {
                        itemChangeDialogShown = false
                        currentDialogType = null
                    })
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
                selectedBefore = selected
                currentDialogType = "goal"
                itemChangeDialogShown = true
            }
        )
        ProfileMenuItem(
            title = "Еженедельная цель",
            subTitle = "-0.5 кг",
            onClick = {
                currentDialogType = "weekly"
                itemChangeDialogShown = true
            }
        )
    }
}


