package ru.itmo.se.mad.ui.layout

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
fun EditableTextRow(
    initialText: String,
    onTextChange: (String) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue(initialText)) }

    // FocusRequester для запроса фокуса
    val focusRequester = remember { FocusRequester() }

    val hintColor by animateColorAsState(
        targetValue = if (isEditing) Color.Transparent else Color.Gray,
        label = "hintFade"
    )

    // Добавляем эффект для установки фокуса при начале редактирования
    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus() // Устанавливаем фокус, если мы начинаем редактировать
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 10.dp, top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isEditing) {
                    isEditing = true // Начинаем редактирование по клику
                }
        ) {
            if (isEditing) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .focusRequester(focusRequester) // Прикрепляем FocusRequester
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFF3B82F6), CircleShape)
                        .clickable {
                            isEditing = false
                            onTextChange(text.text)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Сохранить",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            } else {
                Text(
                    text = text.text,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentWidth()
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Редактировать",
                    tint = Color.Gray,
                    modifier = Modifier.padding(5.dp).size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Нажмите, чтобы изменить",
            fontSize = 14.sp,
            color = hintColor
        )
    }
}
