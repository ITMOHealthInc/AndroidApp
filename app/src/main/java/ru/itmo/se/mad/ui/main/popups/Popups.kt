package ru.itmo.se.mad.ui.main.popups



import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.products.sendWaterMeal
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.WaterBlue40
import ru.itmo.se.mad.ui.theme.WaterBlue70
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray3
import ru.itmo.se.mad.ui.theme.WidgetGray70
import ru.itmo.se.mad.ui.theme.WidgetGray80
import kotlin.math.max
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
    var sliderHeightPx by remember { mutableStateOf(500f) }
    val coroutineScope = rememberCoroutineScope()

    val rawProgress = (0f + dragAmount / sliderHeightPx).coerceIn(minValue, maxValue)
    val snappedValue = (round(rawProgress / 0.05f) * 0.05f).coerceIn(minValue, maxValue)

    val backgroundColor by animateColorAsState(
        targetValue =  WaterBlue40.copy(alpha = 0.2f)
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
                            dragAmount = offset.y
                        },
                        onDragEnd = {
                            isDragging = false
                            val finalValue = (round(
                                (1f + dragAmount / sliderHeightPx).coerceIn(
                                    0f,
                                    maxValue
                                ) / 0.05f
                            ) * 0.05f).coerceIn(minValue, maxValue)
                            if (finalValue > 0f) {
                                onChange(finalValue)
                                coroutineScope.launch {
                                    sendWaterMeal(finalValue * 1000f) // перевод в мл
                                }
                            }

                        },
                        onDragCancel = {
                            isDragging = false
                            dragAmount = 0f
                        },
                        onDrag = { _, drag ->
                            dragAmount += drag.y
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
            coroutineScope.launch {
                sendWaterMeal(snappedValue * 1000f) // перевод в мл
            }
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
                .height(300.dp)
            , // Смещаем вверх для выхода верхнего элемента
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Верхнее значение (частично выходит за границы)
            Box(modifier = Modifier){
                Text(
                    text = "%.2f".format(nextValue),
                    fontSize = 48.sp,
                    color = WaterBlue70,
                    fontFamily = SFProDisplay,
                    modifier = Modifier.height(48.dp)
                )}

            // Центральное выделенное значение
            Text(
                text = "%.2f".format(animatedSnapped),
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold,
                color = WaterBlue,
                fontFamily = SFProDisplay,
                modifier = Modifier.height(48.dp)
            )

            // Нижнее значение (полностью видимое)
            Text(
                text = "%.2f".format(prevValue),
                fontSize = 48.sp,
                color = WaterBlue70,
                fontFamily = SFProDisplay,
                modifier = Modifier
                    .height(48.dp)
                    .zIndex(1f) // Гарантируем рендеринг поверх других элементов
                    .drawWithContent { // Принудительный рендеринг
                        drawContent()
                    }
            )
        }
    }
}
@Composable
private fun ButtonRow(name: String, volume: Float, onAddWater: (Float) -> Unit){
    Row(Modifier.padding(vertical = 8.dp)) {
        Button(
            onClick = {onAddWater(volume)}, shape = CircleShape,
            contentPadding = PaddingValues(0.dp),

            colors = ButtonColors(
                WidgetGray3, Black, White, White
            ), modifier = Modifier
                .size(38.dp)
        ) {
            Icon(Icons.Default.Add, "", tint = WidgetGray80)
        }
        Column (modifier = Modifier.padding(horizontal = 16.dp)){
            Text(
                text = name,
                fontSize = 16.sp,
                color = Black,
                fontWeight = FontWeight.Medium,
                fontFamily = SFProDisplay,
                modifier = Modifier
            )
            Text(
                text = "$volume л",
                fontSize = 14.sp,
                color = WidgetGray70,
                fontFamily = SFProDisplay,
                modifier = Modifier
            )
        }
    }
}