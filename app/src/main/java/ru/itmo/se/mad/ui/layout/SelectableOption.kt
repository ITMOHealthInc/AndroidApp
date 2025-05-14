package ru.itmo.se.mad.ui.layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

@Composable
fun SelectableOption(text: String, selected: Boolean, onClick: () -> Unit) {
    val targetBorderColor = if (selected) Color(0xFF60A5FA) else Color(0x000000)
    val targetBackgroundColor = if (selected) Color(0x2060A5FA) else WidgetGray5
    val targetTextColor = if (selected) Color(0xFF60A5FA) else Color(0xFF000000)

    val borderColor by animateColorAsState(targetValue = targetBorderColor, animationSpec = tween(durationMillis = 150))
    val backgroundColor by animateColorAsState(targetValue = targetBackgroundColor, animationSpec = tween(durationMillis = 150))
    val textColor by animateColorAsState(targetValue = targetTextColor, animationSpec = tween(durationMillis = 150))

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontFamily = SFProDisplay,
            fontSize = 18.sp,
            color = textColor
        )
    }
}
