package ru.itmo.se.mad.ui.main.main_screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.*
import java.util.Calendar
import kotlin.math.ceil
import ru.itmo.se.mad.R


@Composable
fun CalendarScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val calendarT = Calendar.getInstance()
        val currentMonth = calendarT.get(Calendar.MONTH)
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.height(600.dp)
                    .verticalScroll(rememberScrollState(440 * calendarT.get(Calendar.MONTH)))
            ) {
                calendarT.set(Calendar.MONTH, (currentMonth+12-3)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+12-2)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+12-1)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, currentMonth)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+1)%12)
                CalendarGrid(calendarT)

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            1f to White,
                            endY=120f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Spacer(modifier = Modifier.weight(1f)) // Гибкое пространство сверху

                SummaryInfo(
                )
            }
        }


    }

}

@Composable
fun CalendarGrid( month: Calendar) {
    val monthNames = arrayOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )

    val days: List<DayData> = generateDaysForMonth(month.getActualMaximum(Calendar.DAY_OF_MONTH), month)
    val monthId: Int = month.get(Calendar.MONTH)
    val rows = ceil(days.size / 7f).toInt()
    month.set(Calendar.DAY_OF_MONTH, 1)
    Text(monthNames[monthId] + " " + month.get(Calendar.YEAR).toString(),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Black,
        fontFamily = SFProDisplay,
        modifier = Modifier.padding(top = 5.dp, start = min(55.dp*((month.get(Calendar.DAY_OF_WEEK)+6)%8), 55.dp*4))
    )
    LazyVerticalGrid(
        userScrollEnabled = false,
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height((48.dp * rows).coerceAtLeast(48.dp))
    ) {
        items(days) { day ->
            CalendarDay(day)
        }
    }
}

@Composable
fun CalendarDay(day: DayData) {
    val progressColor = when (day.activityLevel) {
        ActivityLevel.HIGH -> CalorieGreen
        ActivityLevel.NONE -> Color.Transparent
        ActivityLevel.ALERT -> CalorieOverflow
    }
    val backgroundColor = if (day.today) CalorieGreen15 else Color.Transparent

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {
        if (day.dayNumber != 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LinearProgressIndicator(
                    progress = { day.activity },
                    color = progressColor,
                    drawStopIndicator = {},
                    gapSize = 0.dp,
                    strokeCap = StrokeCap.Square,
                    trackColor = CalorieGreen15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp)
                        .clip(RoundedCornerShape(50))
                )
                Text(
                    text = day.dayNumber.toString(),
                    fontSize = 18.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}


@Composable
fun SummaryInfo() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = WidgetGrayF2F2F2
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem(
                title = "Активность",
                value = "13 из 30",
                iconPainter = painterResource(id = R.drawable.image_check)
            )
            SummaryItem(title = "Всего дней",
                value = "245",
                iconPainter = painterResource(id = R.drawable.image_check)
            )
            SummaryItem(
                title = "Вес",
                value = "-2.5 кг",
                iconPainter = painterResource(id = R.drawable.image_weight)
            )
        }
    }
}

@Composable
fun SummaryItem(
    iconPainter: Painter,
    title: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            color = WidgetGray70
        )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            color = Black
        )
    }
}


enum class ActivityLevel {
    HIGH, NONE, ALERT
}

data class DayData(
    val dayNumber: Int,
    val activityLevel: ActivityLevel,
    val activity: Float,
    val today: Boolean

)

fun generateDaysForMonth(daysInMonth: Int, calendar: Calendar = Calendar.getInstance()): List<DayData> {
    val days = mutableListOf<DayData>()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val blankDaysAtStart = if (calendar.get(Calendar.DAY_OF_WEEK) == 1) 6 else calendar.get(Calendar.DAY_OF_WEEK)-2
    val calendarToday: Calendar = Calendar.getInstance()

    for( i in 1..blankDaysAtStart) {
        days.add(DayData(dayNumber = 0, activityLevel = ActivityLevel.NONE, 0f, false))
    }

    for (i in 1..daysInMonth) {
        val activity = when {
            i == 8 -> ActivityLevel.ALERT
            else -> ActivityLevel.HIGH
        }
        calendar.set(Calendar.DAY_OF_MONTH, i)
        if(calendarToday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && calendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
            days.add(DayData(dayNumber = i, activityLevel = activity, if(activity == ActivityLevel.ALERT) 1.0f else Math.random().toFloat(), today = true))
        else if (calendarToday.after(calendar))
            days.add(DayData(dayNumber = i, activityLevel = activity, if(activity == ActivityLevel.ALERT) 1.0f else Math.random().toFloat(), today = false))
        else
            days.add(DayData(dayNumber = i, activityLevel = activity, 0f, today = false))
    }
    return days
}