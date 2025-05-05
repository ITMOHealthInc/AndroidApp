package ru.itmo.se.mad.ui.main.calories

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.*

@Composable
fun CalorieWidgetView(
    caloriesEaten: Int = 1820,
    caloriesBurned: Int = 618,
    calorieGoal: Int = 2366,
    protein: Int = 50,
    fat: Int = 6,
    carbs: Int = 38
) {
    val caloriesRemaining = calorieGoal - caloriesEaten + caloriesBurned
    val progress = caloriesEaten.toFloat() / calorieGoal
    Column() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(CalorieGreen15, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(8.dp))

            // Калории
            LinearProgressIndicator(
                progress = { caloriesEaten.toFloat() / calorieGoal.toFloat() },
                color = CalorieGreen,
                trackColor = CalorieGreen15,
                gapSize = 0.dp,
                strokeCap = StrokeCap.Square,
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Съедено
                CalorieStatBlockAccent(
                    value = caloriesEaten.toString(),
                    label = "Съедено",
                    unit = "ккал"
                )

                // Сожжено
                CalorieStatBlock(
                    value = caloriesBurned.toString(),
                    label = "Сожжено",
                    unit = ""
                )

                // Остаток
                CalorieStatBlock(
                    value = caloriesRemaining.toString(),
                    label = "Остаток",
                    unit = ""
                )
            }
            HorizontalDivider(
                color = WidgetGray10,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 20.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                MacronutrientBar(name = "Белки", value = protein, goal = 120)
                MacronutrientBar(name = "Жиры", value = fat, goal = 180)
                MacronutrientBar(name = "Углеводы", value = carbs, goal = 300)
            }

        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).horizontalScroll(ScrollState(0)),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MealCard(
                title = "Завтрак",
                calories = 328,
                goal = 425,
                items = listOf("Овсянка с клубникой", "Зелёный чай")
            )

            MealCard(
                title = "Обед",
                calories = 516,
                goal = 630,
                items = listOf("Суп с фрикадельками", "Паста с курицей", "Морс")
            )

            MealCard(
                title = "Ужин",
                calories = 320,
                goal = 500,
                items = listOf("Гречка с рыбой", "Салат")
            )

            MealCard(
                title = "Перекус",
                calories = 180,
                goal = 250,
                items = listOf("Йогурт", "Орехи")
            )
        }
    }
}
data class MealData(
    val title: String,
    val calories: Int,
    val goal: Int,
    val items: List<String>
)



@Composable
fun MealCard(
    title: String,
    calories: Int,
    goal: Int,
    items: List<String>
) {
    Column(
        modifier = Modifier
            .height(350.dp)
            .background(WidgetGray5, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .width(200.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 10.dp),
                fontFamily = SFProDisplay
            )
            // Кнопка "+"
            IconButton(
                onClick = {},
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = WidgetGray80
                )
            }
        }

        Column (modifier = Modifier.weight(1f)){
            items.forEach { item ->
                Text(item, fontSize = 16.sp,
                    fontFamily = SFProDisplay)
                Spacer(modifier = Modifier.height(2.dp)
                )
            }
        }
        HorizontalDivider(
            color = WidgetGray10,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth().padding(6.dp)
        )

        Column(
            modifier = Modifier
                .padding(6.dp)
        ){

            Row(modifier = Modifier
                .padding(vertical = 10.dp)){
                Text("$calories/$goal",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Black,

                    fontFamily = SFProDisplay
                )
                Text(" ккал",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WidgetGray70,
                    modifier = Modifier.align(Alignment.Bottom),
                    fontFamily = SFProDisplay
                )
            }
            LinearProgressIndicator(
                progress = { calories.toFloat()/goal.toFloat() },
                strokeCap = StrokeCap.Square,
                drawStopIndicator = {},
                gapSize = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = WaterBlue,
                trackColor = WaterBlue10
            )
        }

    }
}

@Composable
fun MacronutrientBar(name: String, value: Int, goal: Int) {
    val ratio = value.toFloat() / goal
    Column(modifier = Modifier) {
        LinearProgressIndicator(
            progress = { value.toFloat() / goal.toFloat() },
            strokeCap = StrokeCap.Square,
            gapSize = 0.dp,
            drawStopIndicator = {},
            modifier = Modifier
                .width(100.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = WidgetGray70,
            trackColor = WidgetGray5,
        )
        Spacer(modifier = Modifier.height(9.dp))
        Row {
            Text(
                text = "$value",
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                color = WidgetGray70,
                fontFamily = SFProDisplay
            )
            Text(
                text = "/$goal г",
                fontSize = 21.sp,
                fontFamily = SFProDisplay,
                color = WidgetGray4560
            )
        }
        Text(
            text = name,
            fontSize = 12.sp,
            color = WidgetGray0060
        )
    }
}
@Composable
fun CalorieStatBlockAccent(
    value: String,
    label: String,
    unit: String,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            fontFamily = SFProDisplay,
            text = "$value $unit",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = CalorieGreen
        )
        Text(
            fontFamily = SFProDisplay,
            text = label,
            fontSize = 12.sp,
            color = WidgetGray0060,
        )
    }
}

@Composable
fun CalorieStatBlock(
    value: String,
    label: String,
    unit: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = Black,
            fontFamily = SFProDisplay
        )
        Text(
            text = "$label$unit",
            fontSize = 12.sp,
            color = WidgetGray0060,
            fontFamily = SFProDisplay
        )
    }
}
