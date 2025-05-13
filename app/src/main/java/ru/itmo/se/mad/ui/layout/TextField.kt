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
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(WidgetGray5, RoundedCornerShape(16.dp))
            .padding(top = 10.dp, end = 20.dp, bottom = 10.dp, start = 8.dp)
    ) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 24.sp,
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
                fontSize = 24.sp,
                fontFamily = SFProDisplay,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
