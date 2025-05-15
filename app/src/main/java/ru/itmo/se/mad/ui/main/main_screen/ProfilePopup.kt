package ru.itmo.se.mad.ui.main.main_screen

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil3.compose.AsyncImage
import ru.itmo.se.mad.R
import ru.itmo.se.mad.model.AuthViewModel
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.theme.*
import androidx.compose.runtime.getValue


@Composable
fun ProfilePopup(
    onClose: () -> Unit,
    storage: OnboardingViewModel,
    oauthStorage: AuthViewModel,
    profilePopupTitle: MutableState<String>,
    popupNavController: NavHostController
) {
    val navBackStackEntry by popupNavController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        profilePopupTitle.value = when (navBackStackEntry?.destination?.route) {
            "account" -> "Аккаунт"
            "goals" -> "Ваши цели"
            else -> ""
        }
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        NavHost(navController = popupNavController,
            startDestination = "main",
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
            }) {
            composable("main") {
                ProfileMainScreen(
                    storage = storage,
                    oauthStorage = oauthStorage,
                    onNavigate = { route ->
                        popupNavController.navigate(route)
                    }
                )
            }
            composable("account") { AccountScreen(storage, oauthStorage) }
            composable("goals") { GoalsScreen(storage, oauthStorage) }
        }
    }
}


@Composable
fun ProfileMainScreen(
    storage: OnboardingViewModel,
    oauthStorage: AuthViewModel,
    onNavigate: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = storage.photoUri,
            placeholder = painterResource(id = R.drawable.bshvevgn),
            error = painterResource(id = R.drawable.icon_user),
            contentDescription = "Profile image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = oauthStorage.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
            fontFamily = SFProDisplay,
            color = Color.Black
        )
        Spacer(Modifier.height(35.dp))
        Row(
            Modifier.fillMaxWidth().height(85.dp),
        ) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(ActivityOrange15)
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text("Дни активности", color = ActivityOrange85, fontSize = 16.sp, fontWeight = FontWeight.W500, fontFamily = SFProDisplay)
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_activity),
                        contentDescription = null,
                        tint = ActivityOrange85
                    )
                    Text(" 12", color = ActivityOrange85, fontWeight = FontWeight.W500, fontSize = 24.sp, fontFamily = SFProDisplay)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(
                Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.verticalGradient(listOf(ProfileDarkGray, ProfileLightGray)))
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    Column {
                        Text("Награды", color = White, fontSize = 16.sp, fontWeight = FontWeight.W500, fontFamily = SFProDisplay)
                        Spacer(Modifier.height(8.dp))
                        Text("8", color = White, fontWeight = FontWeight.W500, fontSize = 24.sp)
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.image_achievement),
                        contentDescription = "AchievementIcon",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(100.dp)
                            .offset(x = 0.dp, y = 20.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(WidgetGray5)
                .padding(16.dp)
        ) {
            Text("Ваши данные", color = Black, fontSize = 16.sp, fontWeight = FontWeight.W500, fontFamily = SFProDisplay)
            Spacer(Modifier.height(12.dp))
            Text("${storage.height} см · ${storage.weight} кг", color = WidgetGray0060, fontSize = 16.sp, fontWeight = FontWeight.W400, fontFamily = SFProDisplay)
            Spacer(Modifier.height(6.dp))
            Text("Полный рацион", color = WidgetGray0060, fontSize = 16.sp, fontWeight = FontWeight.W400, fontFamily = SFProDisplay)
        }
        Spacer(Modifier.height(36.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ProfileMenuItem("Аккаунт") { onNavigate("account") }
            ProfileMenuItem("Ваши цели") { onNavigate("goals") }
        }
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    subTitle: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.W400
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (subTitle.isNotEmpty()) {
                    Text(
                        text = subTitle,
                        fontSize = 18.sp,
                        color = Gray,
                        fontFamily = SFProDisplay,
                        fontWeight = FontWeight.W400
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    tint = Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

