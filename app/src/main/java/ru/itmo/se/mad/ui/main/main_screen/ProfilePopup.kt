package ru.itmo.se.mad.ui.main.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.itmo.se.mad.R
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.theme.*


@Composable
fun ProfilePopup(onClose: () -> Unit, storage:  OnboardingViewModel) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(32.dp))
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
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = storage.name,
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(ActivityOrange15)
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text("Дни активности", color = ActivityOrange85, fontSize = 16.sp,  fontWeight = FontWeight.W500)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_activity),
                            contentDescription = null,
                            tint = ActivityOrange85
                        )
                        Text(" 12", color = ActivityOrange85, fontWeight = FontWeight.W500, fontSize = 24.sp)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.verticalGradient(listOf(ProfileDarkGray, ProfileLightGray)))
                        .padding(16.dp)
                        .weight(1f)
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                        Column{
                            Text("Награды", color = White, fontSize = 16.sp,  fontWeight = FontWeight.W500)
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
                    .clip(RoundedCornerShape(16.dp))
                    .background(WidgetGray5)
                    .padding(16.dp)
            ) {
                Text("Ваши данные", color = Black, fontSize = 16.sp,  fontWeight = FontWeight.W500)
                Spacer(Modifier.height(12.dp))
                Text("${storage.height} см · ${storage.weight} кг", color = WidgetGray0060, fontSize = 16.sp, fontWeight = FontWeight.W400)
                Spacer(Modifier.height(6.dp))
                Text("Полный рацион", color = WidgetGray0060, fontSize = 16.sp, fontWeight = FontWeight.W400)
            }
            Spacer(Modifier.height(16.dp))
            Column(
                Modifier.fillMaxWidth()
            ) {
                ProfileMenuItem("Аккаунт")
                ProfileMenuItem("Ваши цели")
                ProfileMenuItem("Параметры уведомлений")
                ProfileMenuItem("Региональные параметры")
            }
        }
    }
}

@Composable
fun ProfileMenuItem(title: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight =  FontWeight.W400,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = null,
            tint = Black,
            modifier = Modifier.size(20.dp)
        )
    }
} 