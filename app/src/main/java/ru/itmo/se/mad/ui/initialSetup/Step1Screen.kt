package ru.itmo.se.mad.ui.initialSetup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton

@Composable
fun Step1Screen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(title = "Начало пути", label = "Здоровые привычки – это прекрасно.\nДавайте настроим всё за несколько шагов", showBack = false)

        Spacer(Modifier.weight(1f))
        PrimaryButton(text = "Начать", onClick = onNext)
        Spacer(Modifier.height(24.dp))
    }
}

