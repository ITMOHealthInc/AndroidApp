package ru.itmo.se.mad.ui.main.main_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import ru.itmo.se.mad.R
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.model.ProfileViewModel
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.stepsActivity.StepsActivityWidget
import ru.itmo.se.mad.ui.main.water.NewWaterSlider
import ru.itmo.se.mad.ui.theme.ActivityOrange15
import ru.itmo.se.mad.ui.theme.ActivityOrange85
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    onProfileClick: () -> Unit,
    onCalendarClick: () -> Unit
) {

    var currentWater by remember { mutableFloatStateOf(0f) }
    var calories by remember { mutableFloatStateOf(0f) }
    var proteins by remember { mutableFloatStateOf(0f) }
    var fats by remember { mutableFloatStateOf(0f) }
    var carbohydrates by remember { mutableFloatStateOf(0f) }

    val caloriesBurned by remember { mutableFloatStateOf(0f) }
    var calorieGoal by remember { mutableFloatStateOf(3242f) }

    val maxWater = 2.25f

    LaunchedEffect(Unit) {
        try {
            val productsResponse = ApiClient.productsApi.getDailySummary()
            currentWater = productsResponse.totalWater
            calories = productsResponse.totalKbzhu.calories
            proteins = productsResponse.totalKbzhu.proteins
            fats = productsResponse.totalKbzhu.fats
            carbohydrates = productsResponse.totalKbzhu.carbohydrates

            val goalResponse = ApiClient.goalApi.getGoal()
            calorieGoal = goalResponse.calorie_goal.toFloat()

            //caloriesBurned
        } catch (e: Exception) {
            Log.e("dbg", "Ошибка при загрузке: ${e.localizedMessage}", e)
            AlertManager.error("Ошибка при загрузке")
        }
    }


    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White, Color.White, Color.Transparent),
                        startY = 20.0f
                    )
                )
                .padding(16.dp, 50.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = profileViewModel.profilePictureUrl,
                    placeholder = painterResource(id = R.drawable.bshvevgn),
                    error = painterResource(id = R.drawable.icon_user),
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onProfileClick),
                    contentScale = ContentScale.Crop
                )
                Row(
                    Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .background(ActivityOrange15)
                        .padding(10.dp, 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_activity),
                        contentDescription = null,
                        tint = ActivityOrange85,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        " 12",
                        color = ActivityOrange85,
                        fontWeight = FontWeight.W600,
                        fontSize = 16.sp
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(top = 44.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            DateItem(onCalendarClick = onCalendarClick)

            CalorieWidgetView(
                caloriesEaten = calories,
                caloriesBurned = caloriesBurned,
                calorieGoal = calorieGoal,
                protein = proteins,
                fat = fats,
                carbs = carbohydrates
            )
            NewWaterSlider(
                totalDrunk = currentWater,
                maxWater = maxWater,
                onAddWater = { added ->
                    currentWater += added
                }
            )
            StepsActivityWidget()
            Button(
                colors = ButtonColors(
                    containerColor = WidgetGray5,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Black
                ),
                onClick = {},
                modifier = Modifier
                    .padding(vertical = 40.dp)
            ) {
                Text(
                    "Изменить порядок", style = TextStyle(
                        fontFamily = SFProDisplay,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
