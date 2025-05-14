package ru.itmo.se.mad.ui.main.water

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterTrackerTheme
import ru.itmo.se.mad.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    var isExpanded by remember { mutableStateOf(false) }
    var totalWater by remember { mutableFloatStateOf(0f) }
    val maxWater = 2.25f
    var waterDeltaToSend by remember {  mutableFloatStateOf(0f) }
    LaunchedEffect(waterDeltaToSend) {
        if (waterDeltaToSend > 0) {
            sendWaterMeal(waterDeltaToSend)
            waterDeltaToSend = 0f
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val alphaExpanded by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            label = "expandedAlpha"
        )
        val alphaCollapsed by animateFloatAsState(
            targetValue = if (isExpanded) 0f else 1f,
            label = "collapsedAlpha"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaExpanded)
        ) {
            WaterCard(
                totalWater = totalWater,
                onTotalWaterChange = { newAmount ->
                    if (newAmount > totalWater) {
                        val deltaMl = ((newAmount - totalWater) * 1000)
                        waterDeltaToSend = deltaMl
                        Log.d("DebugTag", waterDeltaToSend.toString())

                    }
                    totalWater = newAmount
                },
                maxWater = maxWater,
                onExpandCollapseClick = { isExpanded = false }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaCollapsed)
        ) {
            WaterSlider(
                totalWater = totalWater,
                onWaterAmountChange = {

                    newAmount ->
                    if (newAmount > totalWater) {
                        val deltaMl = ((newAmount - totalWater) * 1000)
                        waterDeltaToSend = deltaMl
                    }

                    totalWater = newAmount
                },
                maxWater = maxWater,
                onExpandCollapseClick = { isExpanded = true },
                expandable = true
            )
        }
    }
}

@Composable
fun WaterCard(
    totalWater: Float,
    onTotalWaterChange: (Float) -> Unit,
    maxWater: Float,
    onExpandCollapseClick: () -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF00A6FF), shape = RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append("%.1f".format(totalWater))
                    }
                    withStyle(style = SpanStyle(color = Color(0xB3FFFFFF))) {
                        append("/%.2f л".format(maxWater))
                    }
                },
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = SFProDisplay,
                )

            Text(
                "В том числе\n из еды: 0.3 л",
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontFamily = SFProDisplay,
                lineHeight = 18.sp,
                textAlign = TextAlign.End,
                )
        }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                WaterItem(
                    label = "Стакан",
                    volume = "0.25 л",
                    onClick = {
                        if (totalWater + 0.25f <= maxWater) {
                            onTotalWaterChange(totalWater + 0.25f)
                        }
                    }
                )
                WaterItem(
                    label = "Бутылка",
                    volume = "0.35 л",
                    onClick = {
                        if (totalWater + 0.35f <= maxWater) {
                            onTotalWaterChange(totalWater + 0.35f)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Add custom volume */ },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text("Другой объём",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = SFProDisplay
                    )
            }
        }

        ExpandCollapseButton(
            isExpanded = true,
            onClick = onExpandCollapseClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 12.dp, y = (-12).dp)
        )
    }
}

@Composable
fun WaterItem(label: String, volume: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clickable(onClick = onClick)
                .background(Color(0x26FFFFFF), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append(label)
                }
                append("\n")
                withStyle(style = SpanStyle(color = Color(0xB3FFFFFF), fontSize = 14.sp)) {
                    append(volume)
                }
            },
            lineHeight = 16.sp,
            fontSize = 16.sp,
            fontFamily = SFProDisplay,
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun WaterSlider(
    totalWater: Float,
    onWaterAmountChange: (Float) -> Unit,
    maxWater: Float,
    onExpandCollapseClick: () -> Unit,
    expandable: Boolean
) {

    var dragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var maxReachedOffset by remember { mutableFloatStateOf(0f) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        val componentWidth = maxWidth
        val componentWidthPx = with(LocalDensity.current) { componentWidth.toPx() }

        val addedWater = remember { derivedStateOf {
            (maxReachedOffset  / componentWidthPx * 1f).coerceIn(0f, 1f)
        }}

        val fillWidth by animateDpAsState(
            targetValue = (maxReachedOffset  / componentWidthPx * componentWidth.value).dp,
            label = "fillWidth"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF00A6FF))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(fillWidth / componentWidth)
                    .height(80.dp)
                    .clip(RoundedCornerShape(24.dp,  0.dp, 0.dp, 24.dp))
                    .background(Color(0xFF66CCFF))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = {
                                dragging = true
                                dragOffset = 0f
                                maxReachedOffset = 0f
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                if (dragAmount > 0) {
                                    dragOffset = (dragOffset + dragAmount)
                                        .coerceAtMost(componentWidthPx)
                                    maxReachedOffset = maxOf(maxReachedOffset, dragOffset)
                                }

                            },
                            onDragEnd = {
                                val newWater = (totalWater + addedWater.value)
                                    .coerceAtMost(maxWater)
                                if (newWater > totalWater) {
                                    onWaterAmountChange(newWater)
                                }
                                dragOffset = 0f
                                maxReachedOffset = 0f
                                dragging = false
                            }
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(0.9f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (dragging) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_water),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        } else {
                            Text(
                                text = "Проведите,\nчтобы добавить",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = SFProDisplay
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1.1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (dragging) {
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Color(0xB3FFFFFF))) {
                                            append("+ ")
                                        }
                                        withStyle(style = SpanStyle(color = White)) {
                                            append("%.2f л".format(addedWater.value))
                                        }
                                    },
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    fontFamily = SFProDisplay

                                )
                                Text(
                                    text = "Отпустите, чтобы добавить",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 16.sp,
                                    maxLines = 2,
                                    textAlign = TextAlign.End,
                                    fontFamily = SFProDisplay
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp),

                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(color = Color.White)) {
                                            append("%.1f".format(totalWater))
                                        }
                                        withStyle(style = SpanStyle(color = Color(0xB3FFFFFF))) {
                                            append("/%.2f л".format(maxWater))
                                        }
                                    },
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = SFProDisplay

                                )
                            }
                        }
                    }
                }
            }
        }
        if (expandable) {

            ExpandCollapseButton(
                isExpanded = false,
                onClick = onExpandCollapseClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 12.dp, y = (-12).dp)
            )
        }
    }
}

@Composable
fun ExpandCollapseButton(isExpanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand

            ),
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    }
}
