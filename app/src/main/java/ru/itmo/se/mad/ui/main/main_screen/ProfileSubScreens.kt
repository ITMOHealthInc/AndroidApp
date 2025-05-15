package ru.itmo.se.mad.ui.main.main_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.model.AuthViewModel
import ru.itmo.se.mad.model.OnboardingViewModel
import ru.itmo.se.mad.ui.layout.EditableTextRow
import ru.itmo.se.mad.ui.layout.PhotoPicker

@Composable
fun AccountScreen(
    storage: OnboardingViewModel,
    oauthStorage: AuthViewModel
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        storage.photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoPicker(
            imageUri = storage.photoUri,
            onPickImage = { launcher.launch("image/*") }
        )
        Spacer(Modifier.height(16.dp))
        EditableTextRow(
            initialText = oauthStorage.name,
            onTextChange = { newText ->
                println("Изменено на: $newText")
            }
        )

    }
}

@Composable
fun GoalsScreen() {

}

