package ru.itmo.se.mad.ui.main.popups



import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray3
import ru.itmo.se.mad.ui.theme.WidgetGray70
import kotlin.math.round

@Composable
fun GenericPopup(
    maxValue: Float = 200f,
    minValue: Float = 0f,
    step: Float = 5f,
    goal: String? = null,
    onChange: (Float) -> Unit
){
    var dragAmount by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var sliderHeightPx by remember { mutableStateOf(1000f) }
    val coroutineScope = rememberCoroutineScope()

    val rawProgress = (0f + dragAmount / sliderHeightPx).coerceIn(0.0f, 1.0f)
    val snappedValue = (round(rawProgress * maxValue / step) * step).coerceIn(minValue, maxValue)

    val backgroundColor by animateColorAsState(
        targetValue =  WidgetGray3
    )
    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        Box(
            modifier = Modifier
                .clipToBounds()
                .height(130.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset: Offset ->
                            isDragging = true
                        },
                        onDragEnd = {
                            isDragging = false

                        },
                        onDragCancel = {
                            isDragging = false
                        },
                        onDrag = { _, drag ->
                            dragAmount += drag.y * step
                        }
                    )
                }
        ) {
            WaterWheelSelector(currentValue = minValue, maxValue = maxValue, step = step, snappedValue, isDragging)

        }
        if(goal != null){
        Text(
            text = "Ваша дневная цель: $goal л",
            fontSize = 16.sp,
            color = Black,
            fontFamily = SFProDisplay,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .graphicsLayer { clip = false }
        )
        HorizontalDivider(
            color = WidgetGray10,
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 14.dp)
        )}
        Button(onClick = {
            onChange(snappedValue)
        }
            ,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonColors(
                Black, White, WidgetGray70, White
            )

        ) {
            Text(
                text = "Добавить",
                fontSize = 16.sp,
                color = White,
                fontFamily = SFProDisplay,
                modifier = Modifier
            )
        }


    }
}

@Composable
private fun WaterWheelSelector(
    currentValue: Float,
    maxValue: Float,
    step: Float,
    animatedSnapped: Float,
    isDragging: Boolean,
    modifier: Modifier = Modifier
) {
    val items = remember { (0..(maxValue * 100).toInt() step (step*100).toInt()).map { it / 100f } }

    // Находим соседние значения
    val (prevValue, nextValue) = remember(animatedSnapped) {
        val lower = items.filter { it < animatedSnapped }.maxOrNull() ?: 0f
        val upper = items.filter { it > animatedSnapped }.minOrNull() ?: animatedSnapped
        lower to upper
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
        // Фиксированная высота контейнера

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier){
                Text(
                    text = "%.2f".format(nextValue),
                    fontSize = 48.sp,
                    color = Black.copy(alpha=0.4f),
                    fontFamily = SFProDisplay,
                    modifier = Modifier.height(48.dp)
                )}

            // Центральное выделенное значение
            Text(
                text = "%.2f".format(animatedSnapped),
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                fontFamily = SFProDisplay,
                modifier = Modifier.height(48.dp)
            )

            Text(
                text = "%.2f".format(prevValue),
                fontSize = 48.sp,
                color =  Black.copy(alpha=0.4f),
                fontFamily = SFProDisplay,
                modifier = Modifier
                    .height(48.dp)
            )
        }
    }
}
