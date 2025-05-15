package ru.itmo.se.mad.ui.initialSetup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.model.AuthViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.TextField

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onNext: () -> Unit,
    onSignupNext: () -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(true) }

    val title = if (isRegisterMode) "Добро пожаловать" else "Здравствуйте"
    val label = if (isRegisterMode) "Создайте Reova ID" else "Войдите со своим Reova ID"
    val secondaryButtonText = if (isRegisterMode) "Уже есть Reova ID" else "Нет Reova ID"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(targetState = title, transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 200)).togetherWith(fadeOut(animationSpec = tween(durationMillis = 200)))
        }) { animatedTitle ->
            HeaderWithBack(title = animatedTitle, label = label, showBack = false)
        }

        Spacer(Modifier.weight(1f))

        TextField(
            value = viewModel.login,
            onValueChange = { viewModel.login = it },
            placeholder = "Имя пользователя",
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "Пароль",
            isPassword = true,
            regex = Regex("^.{0,8}$")
        )

        Spacer(Modifier.height(40.dp))

        if (viewModel.login.isNotBlank() && viewModel.password.isNotBlank()) {
            PrimaryButton(
                text = if (isRegisterMode) "Зарегистрироваться" else "Войти",
                onClick = {
                    if (isRegisterMode) {
                        onSignupNext()
                    } else {
                        viewModel.login(onSuccess = onNext)
                    }
                }
            )
        }

        Spacer(Modifier.height(4.dp))

        AnimatedContent(targetState = secondaryButtonText, transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 200)).togetherWith(fadeOut(animationSpec = tween(durationMillis = 200)))
        }) { animatedText ->
            SecondaryButton(
                text = animatedText,
                onClick = { isRegisterMode = !isRegisterMode }
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}
