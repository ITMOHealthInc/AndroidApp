package ru.itmo.se.mad.ui.main.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R


data class BottomNavItem(
    val route: String,
    val icon: Painter
)

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", painterResource(id = R.drawable.image_home)),
        BottomNavItem(NavRoutes.AddItem.route, painterResource(id = R.drawable.image_plus)),
        BottomNavItem("measure", painterResource(id = R.drawable.image_menu)),
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)

    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White
                        )
                    )
                ),
            containerColor = Color.Transparent
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    },
                    selected = false,
                    onClick = {
                        navController.navigate(item.route)
                    }
                )
            }
        }
    }
}
