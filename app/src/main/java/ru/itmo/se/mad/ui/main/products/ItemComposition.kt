package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.layout.Popup
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10

@Composable
fun ItemComposition(
    onSelect: (content: @Composable () -> Unit) -> Unit,
    navController: NavController
) {
    HorizontalDivider(color = WidgetGray10)
    Column(modifier = Modifier
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .height(540.dp)
        ){
            items(16) {
                MealCell()
            }
        }
    }
    Box(
        modifier = Modifier
        .graphicsLayer { alpha = 0.99f }
            .drawWithContent {
                val colors = listOf(
                    Color.Transparent,
                    Color.White
                )
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors)
                )
            }
    ) {
        Surface(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 45.dp)
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black
                        )
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(colors)
                        )
                    }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black)
                    .padding(horizontal = 20.dp)
                    .clickable {
                        navController.navigate(NavRoutes.ItemSummary.route)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Добавить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    letterSpacing = 0.sp,
                    lineHeight = 28.sp
                )

                Text(
                    text = "236 ккал",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 0.sp,
                    lineHeight = 28.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    var isAddItemDialogShown by remember { mutableStateOf(false) }
    var popupContent by remember {
        mutableStateOf<(@Composable () -> Unit)?>(null)
    }
    Popup(
        isVisible = true,
        onDismissRequest = {
            isAddItemDialogShown = false
            popupContent = null
        },
        horizontalMargin = 0.dp,
        title = "Состав завтрака",
    ) {
        popupContent?.invoke() ?: ItemComposition (
            onSelect = { popupContent = it },
            navController = rememberNavController()
        )
    }
}
