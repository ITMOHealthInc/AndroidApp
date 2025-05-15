package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGrayE9

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun AddProductScreen(/*navController: NavController = NavController(), */caption: String = "Завтрак") {
    val productCount = remember { mutableIntStateOf(0) }
    val searchTerm = TextFieldState()

    var isItemCountDialogShown by remember { mutableStateOf(false) }
    var popupContent by remember {
        mutableStateOf<(@Composable () -> Unit)?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(White)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Text(
                text = caption, style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )
            )
            Button(
                modifier = Modifier
                    .size(32.dp),
                onClick = { isItemCountDialogShown = true },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonColors(WaterBlue, White, Color.Unspecified, Color.Unspecified)
            ) {
                Text(
                    productCount.intValue.toString(), style = TextStyle(
                        fontFamily = SFProDisplay,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ),
                    color = White
                )
            }
        }
        ProductSearchBar(onQueryChange = {}, onSearch = {})
        val parameters = listOf(
            "Продукты",
            "Приёмы пищи",
            "Рецепты"
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 16.dp)
        ) {
            items(parameters) { parameter ->
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false,
                ) {
                    Button(
                        colors = ButtonColors(Black, White, WidgetGray5, Black),
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        onClick = {}
                    ) {
                        Text(parameter, style = TextStyle(
                            fontFamily = SFProDisplay,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        ))
                    }
                }

            }
        }

        LazyColumn(
            modifier = Modifier
        ) {
            item {
                MealCell()
                ProductCell()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 45.dp)
                    .fillMaxWidth()
                    .height(54.dp),
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0f),
                                    Color.White,
                                    Color.White
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                        .padding(horizontal = 20.dp)
                        .clickable {

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
                        text = "234 ккал",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.8f),
                        letterSpacing = 0.sp,
                        lineHeight = 28.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                onClick = { }
            ) {
                Box(modifier = Modifier.background(Color.Black))
                Row(
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        "Добавить", style = TextStyle(
                            fontFamily = SFProDisplay,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal
                        )
                    )
                    Text("234" + " ккал")
                }

            }
        }
    }
}

@Composable
fun ProductSearchBar(
    query: String = "",
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    placeholder: String = "Поиск продуктов"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(WidgetGrayE9)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = "Search",
            modifier = Modifier.size(16.dp),
            tint = Color.Black.copy(alpha = 0.85f)
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                decorationBox = {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    LaunchedEffect(query) {
        onSearch(query)
    }
}


@Composable
fun MealCell(
    mealName: String = "Блинчики, ветчина и сыр",
    subtitle: String = "Перекрёсток, 1 порция (144 г)",
    initialCount: Int = 1,
    onCountChanged: (Int) -> Unit = {}
) {
    var count by remember { mutableIntStateOf(initialCount) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = mealName, style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                text = subtitle,
                style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(17.dp, 16.dp)
                    .clickable {
                        if (count > 0) {
                            count--
                            onCountChanged(count)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.minus),
                    contentDescription = "Decrease quantity",
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
            Text(
                text = count.toString(), color = White,
                style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        count++
                        onCountChanged(count)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = "Increase quantity",
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductCell(
    productName: String = "Сосиска отварная",
    weight: Int = 100,
    calories: Int = 144,
    initialCount: Int = 1,
    onCountChanged: (Int) -> Unit = {}
) {

    var count by remember { mutableIntStateOf(initialCount) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = productName, style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
            Text(
                text = "$weight г", style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                )
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.1f))
                .height(37.dp)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$calories ккал", style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Box(modifier = Modifier
                .clickable {
                count++
                onCountChanged(count)
            },
            )
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = "Add",
                modifier = Modifier.size(16.dp),
                tint = Color.Black.copy(alpha = 0.85f)
            )
        }
    }
}
