package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.WaterBlue10
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray70

@Preview
@Composable
fun FoodTimeChoiceWidget() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            FoodTimeProgressElement("Завтрак", 600.toFloat(), 1000.toFloat())
        }
        item{
            FoodTimeProgressElement("Обед", 850.toFloat(), 1200.toFloat())
        }
        item {
            FoodTimeProgressElement("Ужин", 1200.toFloat(), 1500.toFloat())
        }
        item {
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
            .height(120.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(WidgetGray5),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ){
        Row(
            modifier = Modifier
                .width(240.dp)
                .padding(horizontal = 18.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(foodTime, style = TextStyle(
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp
            ))
            Icon(Icons.Rounded.Add, "", tint = WidgetGray70, modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .clickable {})
        }

        LinearProgressIndicator(
            modifier = Modifier
                .height(15.dp)
                .width(200.dp)
                .padding(horizontal = 18.dp)
                .clip(RoundedCornerShape(20.dp)),
            color = WaterBlue,
            trackColor = WaterBlue10,
            progress = { percentage },
            gapSize = (-15).dp,
            strokeCap = StrokeCap.Square,
            drawStopIndicator = {},


        )
    }
}
