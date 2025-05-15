package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.DateItem

@Composable
fun ItemSummary(caption: String = "Завтрак") {
    Column {
        DateItem(caption, true, )
        CalorieWidgetView(
            summaryMode = true
        )



    }
}