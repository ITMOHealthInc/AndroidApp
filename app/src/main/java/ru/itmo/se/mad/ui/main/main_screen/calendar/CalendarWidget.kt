package ru.itmo.se.mad.ui.main.main_screen.calendar

import android.util.Log
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
import kotlinx.coroutines.launch
import ru.itmo.se.mad.ui.theme.*
import java.util.Calendar
import kotlin.math.ceil
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.main.stepsActivity.fit.FitApiService


@Composable
fun CalendarScreen(
    active: Int = 13,
    daysAmount: Int = 245,
    calendarApiService: CalendarApiService = CalendarApiService()

) {
    val scope = rememberCoroutineScope()
    var monthStepsData by remember { mutableStateOf<Map<Pair<Int, Int>, CalendarApiService.MonthStepsResponse>>(emptyMap()) }
    suspend fun refreshStepsData(month: Int, year: Int) {
        try {
            val data = calendarApiService.getMonthSteps(month, year)
            data?.let {
                monthStepsData = monthStepsData + (Pair(month, year) to it)
            }
        } catch (e: Exception) {
            Log.e("CalendarScreen", "Error refreshing data", e)
        }
    }
    fun loadVisibleMonths(visibleMonth: Int, year: Int) {
        scope.launch {
            if (!monthStepsData.containsKey(Pair(visibleMonth, year))) {
                refreshStepsData(visibleMonth, year)
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        val calendarT = Calendar.getInstance()
        val currentYear = calendarT.get(Calendar.YEAR)
        val currentMonth = calendarT.get(Calendar.MONTH)

        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.height(600.dp)
                    .verticalScroll(rememberScrollState(440 * currentMonth))
            ) {
                // Отображение последних 3 месяцев и следующих 2
                val monthsToShow = listOf(
                    (currentMonth+12-3)%12 to currentYear,
                    (currentMonth+12-2)%12 to currentYear,
                    (currentMonth+12-1)%12 to currentYear,
                    currentMonth to currentYear,
                    (currentMonth+1)%12 to if (currentMonth == 11) currentYear+1 else currentYear
                )

                monthsToShow.forEach { (month, year) ->
                    loadVisibleMonths(month+1, year)
                    calendarT.set(Calendar.MONTH, month)
                    calendarT.set(Calendar.YEAR, year)
                    CalendarGrid(
                        month = calendarT,
                        stepsData = monthStepsData[Pair(month+1, year)]?.days ?: emptyList()
                    )
                }
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
fun CalendarGrid( month: Calendar,
                  stepsData: List<CalendarApiService.DaySteps>) {
    val monthNames = arrayOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )

    val days: List<DayData> = generateDaysForMonth(month.getActualMaximum(Calendar.DAY_OF_MONTH), month, stepsData)
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

fun generateDaysForMonth(daysInMonth: Int, calendar: Calendar = Calendar.getInstance(),  stepsData: List<CalendarApiService.DaySteps>): List<DayData> {
    val days = mutableListOf<DayData>()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val blankDaysAtStart = if (calendar.get(Calendar.DAY_OF_WEEK) == 1) 6 else calendar.get(Calendar.DAY_OF_WEEK)-2
    val calendarToday: Calendar = Calendar.getInstance()

    for( i in 1..blankDaysAtStart) {
        days.add(DayData(dayNumber = 0, activityLevel = ActivityLevel.NONE, 0f, false))
    }
    for (day in 1..daysInMonth) {
        val dateStr = String.format(
            "%04d-%02d-%02d",
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1, // Месяцы в Calendar начинаются с 0
            day,
        )
        val daySteps = stepsData.find { it.date == dateStr }
        val activityLevel = when {
            daySteps == null  -> ActivityLevel.ALERT
            else -> ActivityLevel.HIGH
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        val progress = daySteps?.let {
            (it.steps.toFloat() / it.goal).coerceAtMost(1f)
        } ?: 0f

        val isToday = calendarToday.get(Calendar.DAY_OF_MONTH) == day &&
                calendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                calendarToday.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)

        val isFuture = calendarToday.before(calendar)

        days.add(DayData(
            dayNumber = day,
            activityLevel = if (isFuture) ActivityLevel.HIGH else activityLevel,
            activity = if (activityLevel == ActivityLevel.ALERT && !isFuture) 1.0f else progress,
            today = isToday
        ))
    }
    return days
}