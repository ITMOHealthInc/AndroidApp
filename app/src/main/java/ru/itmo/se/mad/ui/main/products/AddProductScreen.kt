package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.ui.main.achievements.AchievementTile
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.WaterBlue
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray80EA

@Preview
@Composable
fun AddProductScreen(/*caption: String*/) {
    val productCount = remember { mutableIntStateOf(0) }
    val searchTerm = TextFieldState()

    Column(
        modifier = Modifier.fillMaxWidth().background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 100.dp, start = 20.dp),

        ) {
            Text(text = "Завтрак", style = TextStyle(
//                fontFamily = SFProDisplay,
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal))
            Button(modifier = Modifier
                .background(WaterBlue)
                .size(32.dp)
                .border(width = 0.dp, color = Color.Unspecified, shape = CircleShape),
                onClick = {  }) {
                Text(productCount.intValue.toString(), style = TextStyle(
                    fontFamily = SFProDisplay,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal))
            }

        }
        SimpleSearchBar(textFieldState = searchTerm/*, onSearch = ...*/)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 100.dp, start = 20.dp),
        ) {
            Button(
                modifier = Modifier
                    .background(Color.Black)
                    .border(width = 0.dp, color = Color.Unspecified, shape = RoundedCornerShape(18.dp)),
                onClick = {}
            ) {
                Text("Продукты")
            }
            Button(
                modifier = Modifier
                    .background(Color.Black)
                    .border(width = 0.dp, color = Color.Unspecified, shape = RoundedCornerShape(18.dp)),
                onClick = {}
            ) {
                Text("Приёмы пищи")
            }
            Button(
                modifier = Modifier
                    .background(Color.Black)
                    .border(width = 0.dp, color = Color.Unspecified, shape = RoundedCornerShape(18.dp)),
                onClick = {}
            ) {
                Text("Рецепты")
            }

            Column(

            ) {
                Text("Недавние")
            }
            LazyColumn (
                modifier = Modifier
            ) {
                item {
                    ProductCell()
                }
            }

        }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .graphicsLayer { alpha = 0.99f }
                        .drawWithContent {
                            val colors = listOf(
                                Color.Transparent,
                                Color.White,
                                Color.White
                            )
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(colors),
                                blendMode = BlendMode.Color
                            )
                        },
                    onClick = {  }
                ) {
                    Box(modifier = Modifier.background(Color.Black))
                    Text("Добавить", style = TextStyle(
//                        fontFamily = SFProDisplay,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    ))
                    Text("234" + " ккал")
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
//    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded : Boolean by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    /*onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },*/
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") },
                    onSearch = {  }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {

        }
    }
}


@Composable
fun ProductCell(/*name: String?, quantifier: String, calories : Int?*/) {
    Row(

    ) {
        Column(

        ) {

        }
        Row(
            modifier = Modifier
                .background(WidgetGray5)
                .border(width = 0.dp, color = Color.Unspecified, shape = RoundedCornerShape(12.dp))
        ) {
            Text("108" + " ккал", style = TextStyle(
//                fontFamily = SFProDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            ))
            Button(onClick = {  }) {
                Icon(Icons.Sharp.Add, "", tint = WidgetGray80EA,
                    modifier = Modifier.size(16.dp).clickable {  })
            }

        }
    }
}