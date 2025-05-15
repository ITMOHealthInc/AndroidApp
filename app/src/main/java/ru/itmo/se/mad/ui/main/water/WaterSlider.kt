package ru.itmo.se.mad.ui.main.water

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.itmo.se.mad.R
import ru.itmo.se.mad.api.products.sendWaterMeal
import ru.itmo.se.mad.ui.theme.SFProDisplay
import kotlin.math.round

@Composable
fun NewWaterSlider(
    totalDrunk: Float,
    maxWater: Float = 2.25f,
    onAddWater: (Float) -> Unit
) {
    var dragAmount by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var sliderWidthPx by remember { mutableStateOf(1f) }
    val coroutineScope = rememberCoroutineScope()


    val rawProgress = (dragAmount / sliderWidthPx).coerceIn(0f, 1f)
    val snappedValue = (round(rawProgress / 0.05f) * 0.05f).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(targetValue = rawProgress, label = "progress")
    val animatedSnapped by animateFloatAsState(targetValue = snappedValue, label = "snapped")

    val backgroundColor by animateColorAsState(
        targetValue = if (isDragging) Color(0xFF6ECBFF) else Color(0xFF00A3FF),
        label = "background"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .onGloballyPositioned {
                    sliderWidthPx = it.size.width.toFloat()
                }
                .clip(RoundedCornerShape(26.dp))
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset: Offset ->
                            isDragging = true
                            dragAmount = offset.x
                        },
                        onDragEnd = {
                            isDragging = false
                            val finalValue = (round(
                                (dragAmount / sliderWidthPx).coerceIn(
                                    0f,
                                    1f
                                ) / 0.05f
                            ) * 0.05f).coerceIn(0f, 1f)
                            if (finalValue > 0f) {
                                onAddWater(finalValue)
                                coroutineScope.launch {
                                    sendWaterMeal(finalValue * 1000f)
                                }
                            }
                            dragAmount = 0f
                        },
                        onDragCancel = {
                            isDragging = false
                            dragAmount = 0f
                        },
                        onDrag = { _, drag ->
                            dragAmount += drag.x
                        }
                    )
                }

        ) {
            if (isDragging) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = animatedProgress)
                        .background(Color(0xFF00A3FF))
                        .clip(RoundedCornerShape(40.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isDragging) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_water),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        // Animated snapped value
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color(0xB3FFFFFF))) {
                                    append("+ ")
                                }
                                withStyle(style = SpanStyle(color = Color.White)) {
                                    append("%.2f л".format(animatedSnapped))
                                }
                            },
                            fontSize = 34.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = SFProDisplay,
                        )
                        Text(
                            text = "Отпустите, чтобы добавить",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontFamily = SFProDisplay,
                        )
                    }
                } else {
                    Text(
                        text = "Проведите,\nчтобы добавить",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = SFProDisplay,
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append("%.2f".format(totalDrunk))
                            }
                            withStyle(style = SpanStyle(color = Color(0xB3FFFFFF))) {
                                append("/%.2f л".format(maxWater))
                            }
                        },
                        fontSize = 34.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = SFProDisplay,
                    )
                }
            }
        }
    }
}
