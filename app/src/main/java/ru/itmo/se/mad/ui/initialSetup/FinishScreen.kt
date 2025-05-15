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
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton

@Composable
fun FinishScreen(
    onNext: () -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderWithBack(title = "Всё готово", label = "Мы подготовили ваш индивидуальный план.\nВы можете изменить его позже в Профиле", showBack = true, onBackClick = onBack)

        Spacer(Modifier.weight(1f))
        PrimaryButton(text = "Приступим", onClick = onNext)
        Spacer(Modifier.height(16.dp))
    }
}

