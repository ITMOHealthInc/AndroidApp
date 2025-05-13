package ru.itmo.se.mad.ui.initialSetup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.storage.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PhotoPicker
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.theme.SFProDisplay

@Composable
fun Step3Screen(
    viewModel: OnboardingViewModel,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        viewModel.photoUri = uri
    }

    val name = viewModel.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(title = "Добавим фото?", label = "Героев нужно знать в лицо", showBack = false)
        Spacer(Modifier.height(100.dp))

        PhotoPicker(
            imageUri = viewModel.photoUri,
            onPickImage = { launcher.launch("image/*") }
        )

        Spacer(Modifier.height(16.dp))
        Text(name, fontSize = 24.sp, fontWeight = FontWeight.Medium, fontFamily = SFProDisplay)

        Spacer(Modifier.weight(1f))
        if (name !== "") PrimaryButton(text = "Далее", onClick = onNext)
        Spacer(Modifier.height(4.dp))
        SecondaryButton(text = "Пропустить", onClick = onNext)
        Spacer(Modifier.height(24.dp))

    }
}

