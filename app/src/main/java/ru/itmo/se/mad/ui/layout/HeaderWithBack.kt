package ru.itmo.se.mad.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun HeaderWithBack(
    title: String,
    label: String,
    showBack: Boolean = true,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color(0x01B9FF),
                    shape = RoundedCornerShape(50)
                )
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Назад",
                fontSize = 16.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF019EFF),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = SFProDisplay
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = SFProDisplay
        )
    }
}
