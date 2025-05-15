package ru.itmo.se.mad.ui.layout

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.products.ItemComposition
import ru.itmo.se.mad.ui.theme.*

@Composable
fun Popup(
    isVisible: Boolean,
    title: String,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    bottomOffset: Dp = 60.dp,
    horizontalMargin: Dp = 16.dp,
    position: CustomDialogPosition = CustomDialogPosition.BOTTOM,
    content: @Composable () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = properties.dismissOnBackPress,
            dismissOnClickOutside = properties.dismissOnClickOutside,
            securePolicy = properties.securePolicy,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 300),
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 300),
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                Box(
                    modifier = Modifier
                        .customDialogModifier(position, horizontalMargin)
                        .fillMaxWidth()
                        .padding(bottom = bottomOffset)
                ) {
                    Column(
                        modifier = Modifier
                            .width(screenWidth - horizontalMargin * 2)
                            .background(Color.White, shape = RoundedCornerShape(32.dp))
                            .padding(top = 5.dp, end = 20.dp, bottom = 20.dp, start = 20.dp)
                            .animateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 30.dp, height = 5.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(WidgetGray10)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = title,
                                fontSize = 22.sp,
                                fontFamily = SFProDisplay,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(WidgetGray5, CircleShape)
                                    .clickable(onClick = onDismissRequest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close",
                                    tint = WidgetGray80,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        content()
                    }
                }
            }
        }
    }
}

enum class CustomDialogPosition {
    BOTTOM, TOP
}

fun Modifier.customDialogModifier(pos: CustomDialogPosition, horizontalMargin: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(constraints.maxWidth, constraints.maxHeight) {
        when (pos) {
            CustomDialogPosition.BOTTOM -> {
                placeable.place(horizontalMargin.roundToPx(), constraints.maxHeight - placeable.height)
            }
            CustomDialogPosition.TOP -> {
                placeable.place(horizontalMargin.roundToPx(), 0)
            }
        }
    }
}

