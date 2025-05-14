package ru.itmo.se.mad.ui.alert

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.ui.theme.AlertError
import ru.itmo.se.mad.ui.theme.AlertInfo
import ru.itmo.se.mad.ui.theme.AlertSuccess
import ru.itmo.se.mad.ui.theme.AlertWarning

fun getAlertColor(type: AlertType): Color {
    return when (type) {
        AlertType.INFO -> AlertInfo
        AlertType.SUCCESS -> AlertSuccess
        AlertType.WARNING -> AlertWarning
        AlertType.ERROR -> AlertError
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomAlert(
    visible: Boolean,
    message: String,
    type: AlertType,
    onDismiss: () -> Unit
) {
    val backgroundColor = getAlertColor(type)
    val transition = updateTransition(targetState = visible, label = "AlertTransition")

    val offsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        label = "OffsetY"
    ) { isVisible -> if (isVisible) 0.dp else 200.dp }

    if (visible || transition.currentState || transition.targetState) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.StartToEnd) ||
                dismissState.isDismissed(DismissDirection.EndToStart)
            ) {
                onDismiss()
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.StartToEnd,
                    DismissDirection.EndToStart
                ),
                background = {},
                dismissContent = {
                    Box(
                        modifier = Modifier
                            .offset(y = offsetY)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(backgroundColor)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = message,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Закрыть",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}