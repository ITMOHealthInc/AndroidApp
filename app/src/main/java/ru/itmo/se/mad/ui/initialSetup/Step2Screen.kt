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
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.TextField

@Composable
fun Step2Screen(
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
        HeaderWithBack(title = "Давайте познакомимся", label = "Как вас зовут?", showBack = false)

        Spacer(Modifier.weight(1f))

        TextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            placeholder = "Имя"
        )
        Spacer(Modifier.height(40.dp))
        if (viewModel.name !== "") PrimaryButton(text = "Далее", onClick = onNext)
        Spacer(Modifier.height(24.dp))

    }
}

