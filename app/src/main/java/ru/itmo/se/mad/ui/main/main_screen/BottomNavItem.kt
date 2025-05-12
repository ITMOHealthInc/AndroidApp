package ru.itmo.se.mad.ui.main.main_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.WidgetGray80EA
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.semantics.Role


data class BottomNavItem(
    val route: String,
    val icon: Painter
)

@Composable
fun BottomNavBar(onAddItemClick: () -> Unit, onNavigate: (String) -> Unit) {
    val items = listOf(
        BottomNavItem("home", painterResource(id = R.drawable.image_home)),
        BottomNavItem(NavRoutes.AddItem.route, painterResource(id = R.drawable.image_plus)),
        BottomNavItem("measure", painterResource(id = R.drawable.image_menu)),
    )

    var selectedItemIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)

    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .graphicsLayer { alpha = 0.99f }
                .drawWithContent {
                    val colors = listOf(
                        Color.Transparent,
                        Color.White,
                        Color.White
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(colors),
                        blendMode = BlendMode.Color
                    )
                },
            containerColor = Color.Transparent
        ) {
            items.forEach { item ->
                val alpha by animateFloatAsState(
                    targetValue = if (selectedItemIndex == items.indexOf(item)) 1f else 0.6f,
                    label = "alpha"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            role = Role.Button
                        ) {
                            selectedItemIndex = items.indexOf(item)
                            if (item.route == NavRoutes.AddItem.route) {
                                onAddItemClick()
                            } else {
                                onNavigate(item.route)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (item.route == NavRoutes.AddItem.route) {
                        Box(
                            modifier = Modifier
                                .size(width = 67.dp, height = 40.dp)
                                .background(
                                    color = WidgetGray80EA,
                                    shape = RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color.Black
                            )
                        }
                    } else {
                        Icon(
                            painter = item.icon,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(alpha),
                            tint = Color.Black
                        )
                    }
                }


            }
        }
    }
}
