package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
//                        SendRequestComposable("https://data-api.oxilor.com/rest/countries")
                    }
                }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Какое-нибудь другое слово $name!",
//        fontSize = 100.sp,
//        lineHeight = 116.sp,
        modifier = modifier
    )

    Button(onClick = { onClickBreakfast() }) {
        Text("Завтрак")
    }
    Button(onClick = { onClick() }) {
        Text("Обед")
    }
    Button(onClick = { onClick() }) {
        Text("Ужин")
    }
    Button(onClick = { onClick() }) {
        Text("Перекус")
    }
}

fun onClick() {
    Toast.makeText(this, "Its pasta!", Toast.LENGTH_SHORT).show();
}

fun onClickBreakfast() {
    Toast.makeText(this, "Its toast!", Toast.LENGTH_SHORT).show();
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Sean Combs")
    }
}

@Composable
fun SendRequestComposable(domain: String) {
    val responseText = remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val API_KEY = ""
                val client = OkHttpClient()
                val request = okhttp3.Request.Builder()
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .url(domain)
                    .get()
                    .build()
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() ?: "Empty Response"
                withContext(Dispatchers.Main) {
                    responseText.value = responseBody
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    responseText.value = e.toString()
                }
            }
        }
    }
    Text(text = responseText.value)
}}