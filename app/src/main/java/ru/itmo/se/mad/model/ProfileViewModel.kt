package ru.itmo.se.mad.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel() : ViewModel() {
    var name: String by mutableStateOf("")
    var username: String by mutableStateOf("")
    var profilePictureUrl: String by mutableStateOf("")
}