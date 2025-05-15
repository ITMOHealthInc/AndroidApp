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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.DateItem
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10

@Composable
fun ItemSummary(caption: String = "Завтрак", navController: NavController = rememberNavController()) {
    val textStyle = TextStyle(
        fontFamily = SFProDisplay,
        fontSize = 18.sp,
        color = Black
    )
    val nutritions = listOf(
        "Углеводы",
        "Клетчатка",
        "Сахар",
        "Добавленный сахар",
        "Жиры",
        "Насыщенные жиры",
        "Полинасыщенные жиры",
        "Другое",
        "Холестерин",
        "Соль",
        "Вода",
        "Этанол",
        "Витамины",
        "Витамин B7",
        "Витамин C",
        "Витамин D",
        "Витамин E",
        "Витамин K",
        "Минералы",
        "Кальций",
        "Магний",
        "Железо",
        "Цинк"
    )
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(White)
    ) {
        DateItem(caption, true)
        CalorieWidgetView(
            summaryMode = true
        )
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProductEntry()
            ProductEntry("Чай зелёный", "1 кружка (237 мл)")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .clickable {  }
            ) {
                Text(
                    text = "и ещё 1 продукт", style = textStyle,
                    color = WaterBlue,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 12.dp)
                )
                Icon(painter = painterResource(R.drawable.ellipsis), "", tint = WaterBlue)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .height(540.dp)
            ) {
                items(nutritions) { nutrition ->
                    NutritionEntry(nutrition, 1.8f, textStyle)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
                    .fillMaxWidth()
                    .height(70.dp),
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
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Готово", style = TextStyle(
                            fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                        ),
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun NutritionEntry(
    entry: String = "Углеводы",
    quantity: Float,
    textStyle: TextStyle,
) {
    val cotegories = listOf(
        "Углеводы",
        "Жиры",
        "Другое",
        "Витамины",
        "Минералы"
    )
    if(cotegories.contains(entry)) {
        Text(entry, style = textStyle, fontWeight = FontWeight.Medium, modifier = Modifier
            .padding(bottom = 4.dp))
    } else if(entry == "Добавленный сахар") {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавленный сахар", style = textStyle, fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 8.dp))
            val value = if(quantity != null) {
                quantity.toString()
            } else {
                "-"
            }
            Text("$value г", style = textStyle, fontWeight = FontWeight.Normal)
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(entry, style = textStyle, fontWeight = FontWeight.Normal)
            Text("$quantity г", style = textStyle, fontWeight = FontWeight.Normal)
        }
    }
}

@Composable
fun ProductEntry(
    productName: String = "Блинчики, ветчина и сыр",
    productDesc: String = "Перекрёсток, 1 порция (144 г)"
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(top = 16.dp)
        ) {
        Text(
            text = productName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            text = productDesc,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemSummaryPreview() {
    ItemSummary("Ужин", rememberNavController())
}