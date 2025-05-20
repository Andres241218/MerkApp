package com.example.merkapp.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class UserPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    init {
        Log.d(TAG, "UserPreferences init block")
        // Create default admin user if no user exists
        if (!isUserLoggedIn()) {
            Log.d(TAG, "No user logged in, creating default admin user")
            saveUser(DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_NAME, DEFAULT_ADMIN_PASSWORD)
        } else {
            Log.d(TAG, "User already logged in")
        }
    }

    fun saveUser(email: String, name: String, password: String) {
        Log.d(TAG, "Saving user: email=$email, name=$name, password=$password")
        preferences.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_NAME, name)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun getUserEmail(): String? {
        val email = preferences.getString(KEY_EMAIL, null)
        Log.d(TAG, "Retrieving user email: $email")
        return email
    }

    fun getUserName(): String? {
        val name = preferences.getString(KEY_NAME, null)
        Log.d(TAG, "Retrieving user name: $name")
        return name
    }

    fun getUserPassword(): String? {
        val password = preferences.getString(KEY_PASSWORD, null)
        Log.d(TAG, "Retrieving user password: $password")
        return password
    }

    fun isUserLoggedIn(): Boolean {
        val isLoggedIn = getUserEmail() != null && getUserPassword() != null
        Log.d(TAG, "Checking if user is logged in: $isLoggedIn")
        return isLoggedIn
    }

    fun clearUser() {
        Log.d(TAG, "Clearing user preferences")
        preferences.edit().clear().apply()
        // Always ensure default admin user exists
        saveUser(DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_NAME, DEFAULT_ADMIN_PASSWORD)
    }

    companion object {
        private const val TAG = "UserPreferences"
        private const val PREF_NAME = "user_preferences"
        private const val KEY_EMAIL = "user_email"
        private const val KEY_NAME = "user_name"
        private const val KEY_PASSWORD = "user_password"
        
        // Default admin user credentials
        const val DEFAULT_ADMIN_EMAIL = "admin@gmail.com"
        const val DEFAULT_ADMIN_NAME = "Administrador"
        const val DEFAULT_ADMIN_PASSWORD = "Admin01"
    }
} 