package ru.itmo.se.mad.ui.main.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Preview
@Composable
fun DateItem(
    caption: String = "Сегодня",
    summaryMode: Boolean = false,
    onCalendarClick: () -> Unit = {}
) {
    val today = LocalDate.now()
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale("ru"))
    val dayMonthFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

    val dayOfWeek = today.format(dayOfWeekFormatter).replaceFirstChar { it.titlecase() }
    val dayMonth = today.format(dayMonthFormatter).uppercase(Locale("ru"))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${dayOfWeek.uppercase()}, $dayMonth",
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.hsl(0F, 0F, 0F, 0.6F)


                )
                Text(
                    text = caption,
                    fontSize = 32.sp,
                    lineHeight = 38.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            if(!summaryMode) {
                IconButton(onClick = onCalendarClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.image_calendar),
                        contentDescription = "Открыть календарь",
                        modifier = Modifier.size(24.dp),
                    )
                }
            } else {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonColors(WidgetGray5, Black, Color.Unspecified, Color.Unspecified),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    onClick = {}
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(intrinsicSize = IntrinsicSize.Min)
                    ) {
                        Text("Сохранить", style = TextStyle(
                            fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Black
                        ))
                        Icon(Icons.Filled.Star, "")
                    }
                }
            }
        }
    }
}
