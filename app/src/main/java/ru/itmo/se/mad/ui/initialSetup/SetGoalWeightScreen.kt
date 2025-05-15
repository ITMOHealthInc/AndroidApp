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
import ru.itmo.se.mad.model.Gender
import ru.itmo.se.mad.model.Goal
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption

@Composable
fun SetGoalWeightScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val goalWeight = viewModel.goalWeight.toDoubleOrNull()
    val currentWeight = viewModel.weight.toDoubleOrNull()
    val isGoalValid = when (viewModel.goal) {
        Goal.WEIGHT_LOSS -> goalWeight != null && currentWeight != null && goalWeight < currentWeight
        Goal.WEIGHT_MAINTENANCE -> goalWeight != null && currentWeight != null && kotlin.math.abs(goalWeight - currentWeight) <= 1.0
        Goal.WEIGHT_GAIN -> goalWeight != null && currentWeight != null && goalWeight > currentWeight
        else -> false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(
            title = "Ваша цель",
            label = "Укажите желаемый вес и мы поможем\nдостичь поставленной цели",
            showBack = true,
            onBackClick = onBack
        )

        Spacer(modifier = Modifier.weight(1f))

        LabeledTextField(
            value = viewModel.goalWeight,
            onValueChange = { viewModel.goalWeight = it },
            placeholder = "Вес",
            labelRight = "кг"
        )

        Spacer(Modifier.height(40.dp))

        if (isGoalValid) {
            PrimaryButton(text = "Далее", onClick = onNext)
        }

        Spacer(Modifier.height(24.dp))
    }
}


