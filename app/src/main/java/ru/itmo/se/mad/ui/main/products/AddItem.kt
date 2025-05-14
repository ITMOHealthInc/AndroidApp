package ru.itmo.se.mad.ui.main.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.itmo.se.mad.R
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.exception.VisibleException
import ru.itmo.se.mad.ui.alert.AlertManager
import ru.itmo.se.mad.ui.alert.AlertType
import ru.itmo.se.mad.ui.main.measure.MeasureWidget
import ru.itmo.se.mad.ui.main.water.NewWaterSlider
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

@Composable
fun AddItem(onSelect: (content: @Composable () -> Unit) -> Unit) {
    var currentWater by remember { mutableFloatStateOf(0f) }
    val maxWater by remember { mutableFloatStateOf(2.25f) }

    val jwtToken: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo1MDAwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6NTAwMCIsInVzZXJuYW1lIjoidXNlciJ9.PXFU57PS94Da36MEVmnbSUIdo9UrJuRCP496Bipn8a0"


    LaunchedEffect(Unit) {
        try {
            val response = ApiClient.summaryApi.getDailySummary("Bearer $jwtToken")
            currentWater = response.totalWater

        } catch (e: Exception) {
            Log.e("dbg", "Ошибка при загрузке: ${e.localizedMessage}", e)
            AlertManager.show(VisibleException(AlertType.WARNING, "Ошибка при загрузке"))

        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            AddItemElement(
                "Приём пищи",
                R.drawable.image_utensils,
                onClick = { onSelect { FoodTimeChoiceWidget() } }
            )
        }
        item {
            AddItemElement(
                "Активность",
                R.drawable.image_activity,
                onClick = { onSelect { /* Активность */ } }
            )
        }
        item {
            AddItemElement(
                "Измерение",
                R.drawable.image_ruler,
                onClick = { onSelect { MeasureWidget() } }
            )
        }
        item {
            AddItemElement(
                "Вода",
                R.drawable.image_water,
                onClick = { onSelect { NewWaterSlider(
                    totalDrunk = currentWater,
                    maxWater = maxWater,
                    onAddWater = { added ->
                        currentWater = (currentWater + added).coerceAtMost(maxWater)
                    }
                ) } }
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 10.dp, top = 30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_qr),
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        )
        Text(
            "Сканировать код",
            fontFamily = SFProDisplay,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AddItemElement(
    thingType: String = "Приём пищи",
    imageResId: Int = R.drawable.image_utensils,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(WidgetGray5)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = thingType,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.SemiBold
        )
    }
}
