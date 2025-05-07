package ru.itmo.se.mad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.BottomNavBar
import ru.itmo.se.mad.ui.main.main_screen.DateItem
import ru.itmo.se.mad.ui.main.measure.MeasureWidget
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.products.FoodTimeChoiceWidget
import ru.itmo.se.mad.ui.main.stepsActivity.StepsActivityWidget
import ru.itmo.se.mad.ui.main.water.MainScreen
import ru.itmo.se.mad.ui.main.water.WaterSlider
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterTrackerTheme
import ru.itmo.se.mad.ui.theme.WidgetGray5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main() {
//    val navController = rememberNavController()
//    Column(Modifier.padding(top = 10.dp).verticalScroll(rememberScrollState())) {
//        NavHost(navController, startDestination = NavRoutes.AddItem.route) {
//            composable(NavRoutes.AddItem.route) { AddItem(navController) }
//            composable(NavRoutes.FoodTimeChoiceWidget.route) { FoodTimeChoiceWidget()  }
//            composable(NavRoutes.MeasureWidget.route) { MeasureWidget()  }
//        }
//        CalorieWidgetView()
//    }
    val navController = rememberNavController()
    var isExpanded by remember { mutableStateOf(false) }
    var totalWater by remember { mutableFloatStateOf(0.5f) }
    val maxWater = 2.25f


    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { _ ->
        NavHost(
            navController,
            startDestination = "home"
        ) {
            composable("home") {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DateItem(onCalendarClick = {
                        // TODO: логика при нажатии на календарь
                    })
                    CalorieWidgetView()
                    WaterSlider(
                        totalWater = totalWater,
                        onWaterAmountChange = { newAmount -> totalWater = newAmount },
                        maxWater = maxWater,
                        onExpandCollapseClick = { isExpanded = true },
                        expandable = false
                    )
                    StepsActivityWidget()
                    Button(
                        colors = ButtonColors(
                            containerColor = WidgetGray5,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Unspecified,
                            disabledContentColor = Color.Black),
                        onClick = {},
                        modifier = Modifier
                            .padding(vertical = 40.dp)
                    ) {
                        Text("Изменить порядок", style = TextStyle(
                            fontFamily = SFProDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ) )
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                }
            }
            composable(NavRoutes.AddItem.route) {
                AddItem(navController)
            }
            composable(NavRoutes.FoodTimeChoiceWidget.route) {
                FoodTimeChoiceWidget()
            }
            composable("measure") {
                MeasureWidget()
            }
            composable(NavRoutes.AddWaterWidget.route) { WaterTrackerTheme (
                darkTheme = false,
                content = {
                    MainScreen()
                }
            )
            }
        }
    }
}

sealed class NavRoutes(val route: String) {
    data object FoodTimeChoiceWidget : NavRoutes("FoodTimeChoiceWidget")
    data object AddItem : NavRoutes("AddItem")
    data object AddWaterWidget : NavRoutes("AddWaterWidget")
    data object MeasureWidget : NavRoutes("MeasureWidget")
    data object AchievementDetails : NavRoutes("AchievementDetails")
}
