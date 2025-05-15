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
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.SelectableOption

@Composable
fun ChooseSexScreen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val selected = viewModel.gender
    val options = listOf(Gender.MALE, Gender.FEMALE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(title = "Последний штрих", label = "Укажите ваш пол", showBack = false, onBackClick = onBack)

        Spacer(Modifier.weight(1f))
        options.forEach {
            SelectableOption(
                text = it.displayName,
                selected = selected == it,
                onClick = { viewModel.gender = it }
            )
            Spacer(Modifier.height(8.dp))
        }
        Spacer(Modifier.height(40.dp))
        if (viewModel.gender != Gender.NOT_SELECTED) {
            PrimaryButton(text = "Далее", onClick = onNext)
        } else {
            SecondaryButton(text = "Пропустить", onClick = onNext)
        }
        Spacer(Modifier.height(16.dp))
    }
}


