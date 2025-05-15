package ru.itmo.se.mad.ui.main.stepsActivity

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.itmo.se.mad.R
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.fit.StepsResponse
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.theme.ActivityOrange15
import ru.itmo.se.mad.ui.theme.ActivityOrange85
import ru.itmo.se.mad.ui.theme.BackgroundGray4560
import ru.itmo.se.mad.ui.theme.BackgroundGray53
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

@Composable
fun StepsActivityWidget(
    modifier: Modifier = Modifier,
    steps: Int = 0,
    dailyGoal: Int = 10000

) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showGoalDialog by remember { mutableStateOf(false) }
    var showStepsDialog by remember { mutableStateOf(false) }
    var goalInputValue by remember { mutableStateOf(dailyGoal.toString()) }
    var stepsInputValue by remember { mutableStateOf(steps.toString()) }
    var currentDailyGoal by remember { mutableStateOf(dailyGoal) }
    var currentSteps by remember { mutableStateOf(steps) }
    val scope = rememberCoroutineScope()

    suspend fun refreshStepsData() {
        try {
            val response: StepsResponse? = ApiClient.fitApi.getSteps().body()
            Log.e("StepsActivityWidget", response.toString())
            val apiSteps = response?.steps
            val apiGoal = response?.goal
            if (apiSteps != null) {
                currentSteps = apiSteps
            }
            if (apiGoal != null) {
                currentDailyGoal = apiGoal
            }
            goalInputValue = apiGoal?.toString() ?: currentDailyGoal.toString()
            stepsInputValue = apiSteps?.toString() ?: currentSteps.toString()
        } catch (e: Exception) {
            AlertManager.error("Error refreshing data")
            Log.e("StepsActivityWidget", "Error refreshing data", e)
        }
    }

    LaunchedEffect(Unit) {
        refreshStepsData()
    }

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WidgetGray5),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Активность", style = TextStyle(
                        fontFamily = SFProDisplay,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Box {
                    IconButton(
                        onClick = { menuExpanded = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more_horiz_24px),
                            contentDescription = "More options",
                            tint = BackgroundGray4560,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Установить цель шагов", style = TextStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            ))},
                            onClick = {
                                menuExpanded = false
                                showGoalDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Установить количество шагов", style = TextStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            )) },
                            onClick = {
                                menuExpanded = false
                                showStepsDialog = true
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row (
                modifier = Modifier.fillMaxWidth().padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        ) {
                            append(String.format("%,d", currentSteps))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BackgroundGray53
                            )
                        ) {
                            append(" /" + String.format("%,d", currentDailyGoal) + " шагов")
                        }
                    }
                )
                Icon(
                    painter = painterResource(R.drawable.image_google_fit),
                    contentDescription = "Favorite",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {
                val width = size.width
                val height = size.height
                val cornerRadius = CornerRadius(height / 2, height / 2)

                drawRoundRect(
                    color = ActivityOrange15,
                    size = Size(width, height),
                    cornerRadius = cornerRadius
                )

                val progress = (currentSteps / currentDailyGoal.toFloat()).coerceIn(0f, 1f)
                if (progress > 0f) {
                    drawRoundRect(
                        color = ActivityOrange85,
                        size = Size(width * progress, height),
                        cornerRadius = cornerRadius
                    )
                }
            }
        }
    }

    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Установить цель шагов") },
            text = {
                OutlinedTextField(
                    value = goalInputValue,
                    onValueChange = { goalInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Количество шагов") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newGoal = goalInputValue.toIntOrNull() ?: currentDailyGoal
                        scope.launch {
                            try {
                                //Log.e("StepsActivityWidget", ApiClient.fitApi.setDailyGoal(newGoal).toString())
                                ApiClient.fitApi.setDailyGoal(newGoal)
                                refreshStepsData()

                            } catch (e: Exception) {
                                AlertManager.error("Error refreshing data")

                                Log.e("StepsActivityWidget", "Error setting steps", e)
                            }
                        }
                        showGoalDialog = false
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showGoalDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    if (showStepsDialog) {
        AlertDialog(
            onDismissRequest = { showStepsDialog = false },
            title = { Text("Установить количество шагов") },
            text = {
                OutlinedTextField(
                    value = stepsInputValue,
                    onValueChange = { stepsInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Количество шагов") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newSteps = stepsInputValue.toIntOrNull() ?: currentSteps
                        scope.launch {
                            try {
                                //Log.e("StepsActivityWidget", newSteps.toString())

                                ApiClient.fitApi.setSteps(newSteps)
                                refreshStepsData()
                            } catch (e: Exception) {
                                AlertManager.error("Error refreshing data")
                                Log.e("StepsActivityWidget", "Error setting steps", e)
                            }
                        }
                        showStepsDialog = false
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showStepsDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}
