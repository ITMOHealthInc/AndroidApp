package ru.itmo.se.mad.ui.initialSetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.model.Goal
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption

@Composable
fun ChooseGoalScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val options = listOf(Goal.WEIGHT_LOSS, Goal.WEIGHT_MAINTENANCE, Goal.WEIGHT_GAIN)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(title = "Ваша цель", label = "Выберите основную цель", showBack = false, onBackClick = onBack)

        Spacer(Modifier.weight(1f))
        options.forEach {
            SelectableOption(
                text = it.displayName,
                selected = viewModel.goal == it,
                onClick = { viewModel.goal = it }
            )
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(40.dp))
        if (viewModel.goal !== Goal.NOT_SELECTED) PrimaryButton(text = "Далее", onClick = onNext)
        Spacer(Modifier.height(24.dp))
    }
}

