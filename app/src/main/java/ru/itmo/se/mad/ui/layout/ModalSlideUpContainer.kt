package ru.itmo.se.mad.ui.layout

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.zIndex
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ModalSlideUpContainer(
    isVisible: Boolean,
    title: String,
    onClose: () -> Unit,
    isBackButton: Boolean,
    navController: NavHostController,
    content: @Composable (ColumnScope.() -> Unit)
) {

    if (isVisible) {
        Dialog(
            onDismissRequest = onClose,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 20.dp

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300)),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = topPadding)
                        .zIndex(1f),
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        ModalTopAppBar(
                            title = title,
                            onClose = onClose,
                            isBackButton = isBackButton,
                            navController = navController
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            content = content
                        )
                    }
                }
            }
        }
    }
}
