package ru.itmo.se.mad.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    val borderColor = if (selected) Color(0xFF60A5FA) else Color(0x000000)
    val backgroundColor = if (selected) Color(0x2060A5FA) else WidgetGray5
    val textColor = if (selected) Color(0xFF60A5FA) else Color(0xFF000000)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, RoundedCornerShape(14.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(14.dp))
            .padding(vertical = 18.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, fontFamily = SFProDisplay, fontSize = 18.sp, color = textColor)
    }
}
