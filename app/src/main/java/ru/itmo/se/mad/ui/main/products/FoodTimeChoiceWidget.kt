package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.WaterBlue10
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray70
import ru.itmo.se.mad.ui.theme.WidgetGray80

@Preview
@Composable
fun FoodTimeChoiceWidget() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(32.dp))
            .background(color = White)
            .width(450.dp)
            .padding(12.dp)
        ) {
        Box(
            modifier = Modifier
                .size(height = 7.dp, width = 30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
                .padding(5.dp)
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(72.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Text("Что вы хотите добавить?", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Button(onClick = {}, shape = CircleShape, contentPadding = PaddingValues(0.dp), colors = ButtonColors(WidgetGray5, Black, White, White), modifier = Modifier
                .size(32.dp)
                ) {
                Icon(Icons.Filled.Close, "", tint = WidgetGray80)
            }
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            FoodTimeProgressElement("Завтрак", 600.toFloat(), 1000.toFloat())
            FoodTimeProgressElement("Обед", 850.toFloat(), 1200.toFloat())
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            FoodTimeProgressElement("Ужин", 1200.toFloat(), 1500.toFloat())
            FoodTimeProgressElement("Перекус", 200.toFloat(), 600.toFloat())
        }
    }
}

@Composable
fun FoodTimeProgressElement(foodTime: String = "Завтрак", current: Float = 10f, goal: Float = 100f) {
    val percentage = current / goal
    Column(
        modifier = Modifier
            .width(190.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(WidgetGray5),
        verticalArrangement = Arrangement.spacedBy(56.dp)
    ){
        Row(
            modifier = Modifier
                .width(240.dp)
                .padding(horizontal = 18.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(foodTime, fontSize = 20.sp, fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold)
            Icon(Icons.Filled.Add, "", tint = WidgetGray70, modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .clickable {})
        }

        LinearProgressIndicator(
            color = WaterBlue,
            trackColor = WaterBlue10,
            progress = { percentage },
            gapSize = (-15).dp,
            drawStopIndicator = {},
            modifier = Modifier
                .height(16.dp)
                .width(200.dp)
                .padding(horizontal = 18.dp)
        )
    }
}
