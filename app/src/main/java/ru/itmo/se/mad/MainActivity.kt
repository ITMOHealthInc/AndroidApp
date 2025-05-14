package ru.itmo.se.mad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.itmo.se.mad.storage.OauthViewModel
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.initialSetup.*
import ru.itmo.se.mad.ui.layout.ModalSlideUpContainer
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.BottomNavBar
import ru.itmo.se.mad.ui.main.main_screen.CalendarScreen
import ru.itmo.se.mad.ui.main.main_screen.DateItem
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.stepsActivity.StepsActivityWidget
import ru.itmo.se.mad.ui.main.water.NewWaterSlider
import ru.itmo.se.mad.ui.secondaryScreens.OauthScreen
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.main.main_screen.ProfilePopup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import ru.itmo.se.mad.ui.layout.CustomDialogPosition
import ru.itmo.se.mad.ui.theme.ActivityOrange15
import ru.itmo.se.mad.ui.theme.ActivityOrange85
import ru.itmo.se.mad.ui.theme.ProfileDarkGray
import ru.itmo.se.mad.ui.theme.ProfileLightGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Main() {
    val navController = rememberNavController()

    var currentWater by remember { mutableFloatStateOf(0.5f) }
    val maxWater = 2.25f

    var isAddItemDialogShown by remember { mutableStateOf(false) }
    var isProfilePopupShown by remember { mutableStateOf(false) }
    
    var popupContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    var showCalendarModal by remember { mutableStateOf(false) }

    val onboardingViewModel: OnboardingViewModel = viewModel()
    val oauthViewModel: OauthViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute == "home"

    ModalSlideUpContainer(
        isVisible = showCalendarModal,
        title = "Календарь",
        onClose = { showCalendarModal = false }
    ) {
        CalendarScreen()
    }

    Scaffold(
        containerColor = Color.White,
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
                navController = navController,
                startDestination = "oauth",
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                composable("oauth") {
                    OauthScreen(
                        viewModel = oauthViewModel,
                        onNext = { navController.navigate("step1") }
                    )
                }
                composable("step1") {
                    Step1Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step2") }
                    )
                }
                composable("step2") {
                    Step2Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step3") },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("step3") {
                    Step3Screen(
                        viewModel = onboardingViewModel,
                        onNext = { navController.navigate("step4") },
                        onBack = { navController.popBackStack() }
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
                composable("step6") {
                    Step6Screen(
                        viewModel = onboardingViewModel,
                        onNext = {
                            navController.navigate("doneOnboarding") {
                            }
                        },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("doneOnboarding") {
                    DoneScreen(
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
                    Box(Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Brush.verticalGradient(
                                    listOf(Color.White, Color.White, Color.Transparent),
                                    startY = 20.0f
                                    ))
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
                                    model = onboardingViewModel.photoUri,
                                    placeholder = painterResource(id = R.drawable.bshvevgn),
                                    error = painterResource(id = R.drawable.icon_user),
                                    contentDescription = "Profile image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .clickable(onClick = { isProfilePopupShown = true })
                                )
                                Row(
                                    Modifier
                                    .clip(RoundedCornerShape(25.dp))
                                    .background(ActivityOrange15)
                                    .padding(10.dp,  5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_activity),
                                        contentDescription = null,
                                        tint = ActivityOrange85,
                                        modifier = Modifier
                                            .size(16.dp)
                                    )
                                    Text(" 12", color = ActivityOrange85, fontWeight = FontWeight.W600, fontSize = 16.sp)
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
                            DateItem(onCalendarClick = {
                                showCalendarModal = true
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
                    popupContent?.invoke() ?: AddItem(onSelect = { popupContent = it })
                }
            }
            if (isProfilePopupShown) {
                Popup(
                    isVisible = true,
                    onDismissRequest = { isProfilePopupShown = false },
                    title = "",
                    bottomOffset = 0.dp,
                    horizontalMargin = 0.dp,
                ) {
                    ProfilePopup(onClose = { isProfilePopupShown = false }, onboardingViewModel)
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
    data object CalendarWidget : NavRoutes("CalendarWidget")
}
