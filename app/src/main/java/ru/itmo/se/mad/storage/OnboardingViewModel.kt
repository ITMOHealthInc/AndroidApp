package ru.itmo.se.mad.storage

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    var name: String by mutableStateOf("")
    var photoUri: Uri? by mutableStateOf(null)
    var goal: String by mutableStateOf("")
    var height: String by mutableStateOf("")
    var weight: String by mutableStateOf("")
    var gender: String by mutableStateOf("")
}

