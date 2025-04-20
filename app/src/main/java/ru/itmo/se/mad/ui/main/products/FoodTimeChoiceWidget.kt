package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.WaterBlue10
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray3;
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray70
import ru.itmo.se.mad.ui.theme.WidgetGray80

@Preview
@Composable
fun FoodTimeChoiceWidget() {
    Column {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Что вы хотите добавить?", fontSize = 24.sp)
            Button(onClick = {}, colors = ButtonColors(WidgetGray5, Black, White, White)) {
                Icon(Icons.Filled.Close, "", tint = WidgetGray80)
            }
        }
        Row (

        ) {
            FoodTimeProgressElement("Завтрак", 600.toFloat(), 1000.toFloat())
            FoodTimeProgressElement("Обед", 850.toFloat(), 1200.toFloat())
        }
        Row (

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
            .width(220.dp)
            .height(180.dp)
            .background(WidgetGray3)
            .padding(horizontal = 21.dp, vertical = 21.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ){
        Row(
            modifier = Modifier
                .width(240.dp)
                .padding(horizontal = 18.dp, vertical = 18.dp)
                .border(
                    width = 0.dp,
                    color = WidgetGray3,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(foodTime, fontSize = 18.sp, fontFamily = FontFamily.Default)
            Icon(Icons.Filled.Add, "", tint = WidgetGray70, modifier = Modifier
                .width(24.dp)
                .height(24.dp)
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
