package ru.itmo.se.mad.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.se.mad.api.ApiClient
import ru.itmo.se.mad.api.auth.ProfileResponse
import ru.itmo.se.mad.api.auth.UpdateProfileRequest
import ru.itmo.se.mad.storage.LocalStorage

class ProfileViewModel() : ViewModel() {
    var name: String by mutableStateOf("")
    var username: String by mutableStateOf("")
    var profilePictureUrl: Uri? by mutableStateOf(null)

    fun load() {
        viewModelScope.launch {
            val profile = ApiClient.authApi.getProfile()
            fromDto(profile)
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            ApiClient.authApi.updateProfile(UpdateProfileRequest(name = newName))
            name = newName
        }
    }

    private fun fromDto(profile: ProfileResponse) {
        this.name = profile.name
        this.username = profile.username
        this.profilePictureUrl = profile.profilePictureUrl?.toUri()

        if (profilePictureUrl == null) {
            LocalStorage.getPhotoUri()?.let {
                profilePictureUrl = it.toUri()
            }
        }
    }
}