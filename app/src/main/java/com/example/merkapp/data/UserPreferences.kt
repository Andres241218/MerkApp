package com.example.merkapp.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUser(email: String, name: String, password: String) {
        preferences.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun getUserEmail(): String? {
        return preferences.getString(KEY_EMAIL, null)
    }

    fun getUserName(): String? {
        return preferences.getString(KEY_NAME, null)
    }

    fun getUserPassword(): String? {
        return preferences.getString(KEY_PASSWORD, null)
    }

    fun isUserLoggedIn(): Boolean {
        return getUserEmail() != null && getUserPassword() != null
    }

    fun clearUser() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "user_preferences"
        private const val KEY_EMAIL = "user_email"
        private const val KEY_NAME = "user_name"
        private const val KEY_PASSWORD = "user_password"
    }
} 