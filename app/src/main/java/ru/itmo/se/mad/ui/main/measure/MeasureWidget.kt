package ru.itmo.se.mad.ui.main.measure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray80

//@Preview
@Composable
fun MeasureWidget() {
        val parameters = listOf(
            "Вес",
            "Талия",
            "Бёдра",
            "Грудь",
            "Руки",
            "Объём жира",
            "Мышечная масса",
        )

        LazyColumn(
            modifier = Modifier.padding(bottom = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(parameters) { param ->
                ParameterElement(param)
            }

        }
}

@Composable
fun ParameterElement(parameterName: String = "Вес") {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = parameterName,
            fontSize = 18.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Normal,
        )
        Button(onClick = {}, shape = CircleShape,
            contentPadding = PaddingValues(0.dp),

            colors = ButtonColors(
            White, Black, White, White
        ), modifier = Modifier
            .size(32.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "", tint = WidgetGray80)
        }
    }
}