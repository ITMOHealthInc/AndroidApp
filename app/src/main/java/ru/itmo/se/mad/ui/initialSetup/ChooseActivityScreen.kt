package ru.itmo.se.mad.ui.initialSetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.model.ActivityLevel
import ru.itmo.se.mad.model.Goal
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption
import ru.itmo.se.mad.ui.theme.SFProDisplay

@Composable
fun ChooseActivityScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val selected = viewModel.activity
    val options = listOf(ActivityLevel.HIGH, ActivityLevel.NORMAL, ActivityLevel.LOW)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        HeaderWithBack(title = "Уровень активности", label = "Выберите ваш уровень активности", showBack = true, onBackClick = onBack)
        Spacer(Modifier.height(64.dp))
        Text(
            text = "Высокий уровень – ежедневная активность",
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SFProDisplay,
            color = Color.Black,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Нормальный уровень – периодическая активность на протяжении недели",
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SFProDisplay,
            color = Color.Black,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Низкий уровень – редкая активность или её отсутствие",
            fontSize = 18.sp,
            fontWeight = FontWeight.W400,
            fontFamily = SFProDisplay,
            color = Color.Black,
        )
        Spacer(Modifier.weight(1f))
        options.forEach {
            SelectableOption(
                text = it.displayName,
                selected = selected == it,
                onClick = { viewModel.activity = it }
            )
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(40.dp))
        if (viewModel.activity !== ActivityLevel.NOT_SELECTED) PrimaryButton(text = "Далее", onClick = onNext)
        Spacer(Modifier.height(24.dp))
    }
}

