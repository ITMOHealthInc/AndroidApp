package ru.itmo.se.mad

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.itmo.se.mad.model.AuthViewModel
import ru.itmo.se.mad.model.GoalsViewModel
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.model.ProfileViewModel
import ru.itmo.se.mad.ui.initialSetup.*
import ru.itmo.se.mad.ui.layout.ModalSlideUpContainer
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.main.main_screen.BottomNavBar
import ru.itmo.se.mad.ui.main.main_screen.calendar.CalendarScreen
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.initialSetup.AuthScreen
import ru.itmo.se.mad.ui.main.main_screen.ProfilePopup
import ru.itmo.se.mad.ui.initialSetup.NameInputScreen
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.alert.BottomAlert
import ru.itmo.se.mad.storage.LocalStorage
import ru.itmo.se.mad.ui.main.main_screen.HomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalStorage.initialize(this)
        setContent { Main() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedBoxWithConstraintsScope")
@Composable
fun Main() {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        if (LocalStorage.hasToken()) {
            navController.navigate("home")
        }
    }

    var isProfilePopupShown by remember { mutableStateOf(false) }
    val profilePopupTitle = remember { mutableStateOf("") }
    val popupNavController = rememberNavController()
    var showCalendarModal by remember { mutableStateOf(false) }
    val onboardingViewModel: OnboardingViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute == "home"
    val profileViewModel: ProfileViewModel = viewModel()
    val goalsViewModel: GoalsViewModel = viewModel()

    LaunchedEffect(Unit) {
        if (LocalStorage.hasToken()) {
            navController.navigate("home")
        }
    }

    ModalSlideUpContainer(
        isVisible = showCalendarModal,
        title = "Календарь",
        onClose = { showCalendarModal = false },
        isBackButton = false,
        navController = popupNavController
    ) {
        CalendarScreen()
    }

    var isAddItemDialogShown by remember { mutableStateOf(false) }
    var popupContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var popupTitle by remember { mutableStateOf("Что вы хотите добавить?") }

    if (isAddItemDialogShown) {
        Popup(
            isVisible = true,
            onDismissRequest = {
                isAddItemDialogShown = false
                popupContent = null
                popupTitle = "Что вы хотите добавить?"
            },
            title = popupTitle,
        ) {
            popupContent?.invoke() ?: AddItem(
                onSelect = { newContent ->
                    popupContent = newContent
                },
                setTitle = { newTitle ->
                    popupTitle = newTitle
                }
            )
        }
    }

    if (isProfilePopupShown) {
        ModalSlideUpContainer(
            isVisible = isProfilePopupShown,
            title = profilePopupTitle.value,
            onClose = { isProfilePopupShown = false },
            isBackButton = profilePopupTitle.value.isNotEmpty(),
            navController = popupNavController
        ) {
            ProfilePopup(
                profileViewModel,
                goalsViewModel,
                onboardingViewModel,
                profilePopupTitle,
                popupNavController = popupNavController
            )
        }
    }
    Box(Modifier.fillMaxSize()) {
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
                    startDestination = "auth",
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
                    composable("auth") {
                        AuthScreen(
                            viewModel = authViewModel,
                            onNext = { navController.navigate("home") },
                            onSignupNext = { navController.navigate("nameInput") }
                        )
                    }
                    composable("nameInput") {
                        NameInputScreen (
                            viewModel = authViewModel,
                            onNext = { navController.navigate("startMessageStep") },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("startMessageStep") {
                        StartMessageScreen {
                            navController.navigate("addPhotoStep")
                        }
                    }
                    composable("addPhotoStep") {
                        AddPhotoScreen(
                            viewModel = onboardingViewModel,
                            authViewModel = authViewModel,
                            onNext = { navController.navigate("chooseGoalStep") },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("chooseGoalStep") {
                        ChooseGoalScreen(
                            viewModel = onboardingViewModel,
                            onNext = { navController.navigate("chooseActivityLevelStep") },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("chooseActivityLevelStep") {
                        ChooseActivityScreen (
                            viewModel = onboardingViewModel,
                            onNext = {
                                onboardingViewModel.saveGoals()
                                navController.navigate("setMeasuresStep")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("setMeasuresStep") {
                        SetMeasuresScreen(
                            viewModel = onboardingViewModel,
                            onNext = {
                                onboardingViewModel.saveMeasurements()
                                navController.navigate("chooseSexStep")
                            },
                            onBack = { navController.popBackStack() }
                        )
                        BottomAlert(
                            visible = AlertManager.visible,
                            message = AlertManager.message,
                            type = AlertManager.type,
                            onDismiss = { AlertManager.hide() }
                        )
                    }
                    composable("setGoalWeightStep") {
                        SetGoalWeightScreen(
                            viewModel = onboardingViewModel,
                            onNext = {
                                navController.navigate("finishStep")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("chooseSexStep") {
                        ChooseSexScreen(
                            viewModel = onboardingViewModel,
                            onNext = {
                                onboardingViewModel.saveSex()
                                navController.navigate("finishStep")
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("finishStep") {
                        FinishScreen(
                            onNext = {
                                navController.navigate("home") {
                                    popUpTo("step2") { inclusive = true }
                                }
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            storage = onboardingViewModel,
                            profileViewModel = profileViewModel,
                            goalsViewModel = goalsViewModel,
                            onProfileClick = { isProfilePopupShown = true },
                            onCalendarClick = { showCalendarModal = true }
                        )
                    }
                }
            }
        }
        BottomAlert(
            visible = AlertManager.visible,
            message = AlertManager.message,
            type = AlertManager.type,
            onDismiss = { AlertManager.hide() }
        )
    }
}

sealed class NavRoutes(val route: String) {
    data object AddItem : NavRoutes("AddItem")
    data object AchievementDetails : NavRoutes("AchievementDetails")
}
