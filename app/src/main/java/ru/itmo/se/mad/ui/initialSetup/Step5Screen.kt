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
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.LabeledTextField
import ru.itmo.se.mad.ui.layout.PrimaryButton

@Composable
fun Step5Screen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(
            title = "Немного о личном",
            label = "Укажите ваш текущий рост и вес",
            showBack = true,
            onBackClick = onBack
        )

        Spacer(modifier = Modifier.weight(1f))

        LabeledTextField(
            value = viewModel.height,
            onValueChange = { viewModel.height = it },
            placeholder = "Рост",
            labelRight = "см"
        )
        Spacer(modifier = Modifier.height(12.dp))
        LabeledTextField(
            value = viewModel.weight,
            onValueChange = { viewModel.weight = it },
            placeholder = "Вес",
            labelRight = "кг"
        )
        Spacer(Modifier.height(40.dp))
        PrimaryButton(text = "Далее", onClick = onNext)
        Spacer(Modifier.height(24.dp))
    }
}

