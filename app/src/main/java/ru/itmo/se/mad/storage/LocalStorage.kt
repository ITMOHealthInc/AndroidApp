package ru.itmo.se.mad.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


object LocalStorage {
    private const val PREFS_NAME = "secure_storage"
    private const val TOKEN_KEY = "auth_token"
    private const val PHOTO_KEY = "photo_uri"

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
        save(TOKEN_KEY, token)
    }

    fun getToken(): String? {
        return get(TOKEN_KEY)
    }

    fun hasToken(): Boolean {
        return getToken() != null
    }

    fun removeToken() {
        remove(TOKEN_KEY)
    }

    fun savePhotoUri(uri: String) {
        save(PHOTO_KEY, uri)
    }

    fun getPhotoUri(): String? {
        return get(PHOTO_KEY)
    }

    private fun save(key: String, value: String) {
        encryptedPrefs.edit()
            .putString(key, value)
            .apply()
    }

    private fun get(key: String): String? {
        return encryptedPrefs.getString(key, null)
    }

    private fun remove(key: String) {
        encryptedPrefs.edit()
            .remove(key)
            .apply()
    }
}