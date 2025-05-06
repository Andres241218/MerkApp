package com.example.merkapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.merkapp.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    init {
        viewModelScope.launch {
            val saved = application.dataStore.data.first()[DARK_MODE_KEY] ?: false
            _isDarkMode.value = saved
        }
    }

    fun toggleDarkMode() {
        val newValue = !_isDarkMode.value
        _isDarkMode.value = newValue
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { prefs ->
                prefs[DARK_MODE_KEY] = newValue
            }
        }
    }

    fun getIconPrefix(): String {
        return if (_isDarkMode.value) "icw_" else "ic_"
    }
} 