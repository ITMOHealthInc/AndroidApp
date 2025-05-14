package ru.itmo.se.mad.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


object LocalStorage {
    private const val PREFS_NAME = "secure_storage"
    private const val TOKEN_KEY = "auth_token"
    private const val NAME_KEY = "profile_name"

    private lateinit var encryptedPrefs: EncryptedSharedPreferences

    fun initialize(context: Context) {
        if (!::encryptedPrefs.isInitialized) {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            encryptedPrefs = EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ) as EncryptedSharedPreferences
        }
    }

    fun saveToken(token: String) {
        encryptedPrefs.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    fun getToken(): String? {
        return encryptedPrefs.getString(TOKEN_KEY, null)
    }

    fun hasToken(): Boolean {
        return getToken() != null
    }

    fun clearToken() {
        encryptedPrefs.edit()
            .remove(TOKEN_KEY)
            .apply()
    }

    fun saveName(name: String) {
        encryptedPrefs.edit()
            .putString(NAME_KEY, name)
            .apply()
    }

    fun getName(): String? {
        return encryptedPrefs.getString(NAME_KEY, null)
    }
}