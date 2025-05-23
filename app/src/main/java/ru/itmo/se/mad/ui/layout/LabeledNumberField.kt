package ru.itmo.se.mad.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WidgetGray5

@Composable
fun LabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRight: String,
    placeholder: String
) {
    val digitsOnly = remember {
        Regex("[0-9]*")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(WidgetGray5, RoundedCornerShape(20.dp))
            .padding(top = 10.dp, end = 20.dp, bottom = 10.dp, start = 8.dp)
    ) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.matches(digitsOnly)) {
                    onValueChange(newValue)
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 26.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Medium
                )
            },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(
                fontSize = 26.sp,
                fontFamily = SFProDisplay,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = labelRight,
            fontSize = 20.sp,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Medium
        )
    }
}
