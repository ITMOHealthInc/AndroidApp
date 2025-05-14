package ru.itmo.se.mad.ui.alert

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
        transitionSpec = { tween(250) },
        label = "OffsetY"
    ) { isVisible -> if (isVisible) 0.dp else 150.dp }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(250) },
        label = "Alpha"
    ) { isVisible -> if (isVisible) 1f else 0f }

    // Показываем только во время анимации появления/исчезновения
    if (visible || transition.currentState || transition.targetState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .offset(y = offsetY)
                    .alpha(alpha)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp),
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
    }
}