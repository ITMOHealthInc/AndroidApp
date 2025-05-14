package ru.itmo.se.mad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.initialSetup.Step1Screen
import ru.itmo.se.mad.ui.initialSetup.Step2Screen
import ru.itmo.se.mad.ui.initialSetup.Step3Screen
import ru.itmo.se.mad.ui.initialSetup.Step4Screen
import ru.itmo.se.mad.ui.initialSetup.Step5Screen
import ru.itmo.se.mad.ui.initialSetup.Step6Screen
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.BottomNavBar
import ru.itmo.se.mad.ui.main.main_screen.DateItem
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.stepsActivity.StepsActivityWidget
import ru.itmo.se.mad.ui.main.water.NewWaterSlider
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@Composable
fun Main() {
    val navController = rememberNavController()

    var currentWater by remember { mutableFloatStateOf(0.5f) }
    val maxWater = 2.25f

    var isAddItemDialogShown by remember { mutableStateOf(false) }
    var popupContent by remember {
        mutableStateOf<(@Composable () -> Unit)?>(null)
    }

    val onboardingViewModel: OnboardingViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute == "home"

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    onAddItemClick = { isAddItemDialogShown = true },
                    onNavigate = { navController.navigate(it) }
                )
            }
        }
    ) { _ ->
        Box {
            NavHost(
                navController,
                startDestination = "step1"
            ) {
                composable("step1") {
                    Step1Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step2") }
                    )
                }
                composable("step2") {
                    Step2Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step3") }
                    )
                }
                composable("step3") {
                    Step3Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step4") }
                    )
                }
                composable("step4") {
                    Step4Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step5") },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("step5") {
                    Step5Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step6") },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("step5") {
                    Step6Screen(
                        viewModel = onboardingViewModel,
                        onNext = {
                            navController.navigate("home") {
                                popUpTo("step2") { inclusive = true }
                            }
                        },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("home") {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        DateItem(onCalendarClick = {
                            // TODO: логика при нажатии на календарь
                        })
                        CalorieWidgetView()
                        NewWaterSlider(
                            totalDrunk = currentWater,
                            maxWater = maxWater,
                            onAddWater = { added ->
                                currentWater = (currentWater + added).coerceAtMost(maxWater)
                            }
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
                            ))
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }

            if (isAddItemDialogShown) {
                Popup(
                    isVisible = true,
                    onDismissRequest = {
                        isAddItemDialogShown = false
                        popupContent = null
                    },
                    title = "Что вы хотите добавить?",
                ) {
                    popupContent?.invoke() ?: AddItem(
                        onSelect = { popupContent = it }
                    )
                }
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
