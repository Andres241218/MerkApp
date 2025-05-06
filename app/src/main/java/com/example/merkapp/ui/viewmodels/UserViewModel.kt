package com.example.merkapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.merkapp.data.UserPreferences
import com.example.merkapp.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val userName: String? = null
)

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application.applicationContext)
    val dataStore = application.applicationContext.dataStore
    private val _uiState = MutableStateFlow(UserState(isLoggedIn = false))
    val uiState: StateFlow<UserState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            val savedEmail = userPreferences.getUserEmail()
            val savedPassword = userPreferences.getUserPassword()
            
            if (email == savedEmail && password == savedPassword) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    error = null,
                    userName = userPreferences.getUserName()
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "Email o contraseña incorrectos"
                )
            }
        }
    }

    fun register(email: String, name: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            userPreferences.saveUser(email, name, password)
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isLoggedIn = true,
                userName = name,
                error = null
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearUser()
            _uiState.value = UserState(isLoggedIn = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun refreshUser() {
        _uiState.value = _uiState.value.copy(
            userName = userPreferences.getUserName()
        )
    }
} 