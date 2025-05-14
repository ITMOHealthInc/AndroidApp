package ru.itmo.se.mad.ui.secondaryScreens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.storage.OauthViewModel
import ru.itmo.se.mad.ui.layout.HeaderWithBack
import ru.itmo.se.mad.ui.layout.PrimaryButton
import ru.itmo.se.mad.ui.layout.SecondaryButton
import ru.itmo.se.mad.ui.layout.TextField

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OauthScreen(
    viewModel: OauthViewModel,
    onNext: () -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }

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
            placeholder = "Имя пользователя"
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "Пароль"
        )

        Spacer(Modifier.height(40.dp))

        if (viewModel.login.isNotBlank() && viewModel.password.isNotBlank()) {
            PrimaryButton(
                text = if (isRegisterMode) "Зарегистрироваться" else "Войти",
                onClick = {
                    if (isRegisterMode) {
                        viewModel.register(
                            login = viewModel.login,
                            password = viewModel.password,
                            onSuccess = onNext
                        )
                    } else {
                        viewModel.login(
                            login = viewModel.login,
                            password = viewModel.password,
                            onSuccess = onNext
                        )
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
