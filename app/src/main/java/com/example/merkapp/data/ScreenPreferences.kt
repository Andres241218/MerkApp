package com.example.merkapp.data

import android.content.Context
import android.content.SharedPreferences

class ScreenPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun hasSeenScreen(screenKey: String): Boolean {
        return preferences.getBoolean(screenKey, false)
    }

    fun markScreenAsSeen(screenKey: String) {
        preferences.edit().putBoolean(screenKey, true).apply()
    }

    companion object {
        private const val PREF_NAME = "screen_preferences"
        
        const val MAIN_SCREEN_SEEN = "main_screen_seen"
        const val MY_LISTS_SCREEN_SEEN = "my_lists_screen_seen"
        const val CONFIG_SCREEN_SEEN = "config_screen_seen"
        // Add key for 'Mi Lista' screen if found
    }
} 