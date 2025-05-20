package com.example.merkapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.merkapp.data.UserPreferences
import com.example.merkapp.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull

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
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // Validar email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Email inválido"
                    )
                    return@launch
                }

                // Validar contraseña
                if (password.length < 6) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "La contraseña debe tener al menos 6 caracteres"
                    )
                    return@launch
                }

                // Obtener credenciales guardadas
                val savedEmail = try {
                    userPreferences.getUserEmail()
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Error al obtener las credenciales guardadas"
                    )
                    return@launch
                }

                val savedPassword = try {
                    userPreferences.getUserPassword()
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Error al obtener las credenciales guardadas"
                    )
                    return@launch
                }
                
                if (email == savedEmail && password == savedPassword) {
                    val userName = try {
                        userPreferences.getUserName()
                    } catch (e: Exception) {
                        null
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        error = null,
                        userName = userName
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        error = "Email o contraseña incorrectos"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
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