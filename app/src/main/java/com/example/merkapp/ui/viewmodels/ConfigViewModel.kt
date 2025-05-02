package com.example.merkapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.merkapp.data.UserPreferences

class ConfigViewModel(context: Context) : ViewModel() {
    private val userPreferences = UserPreferences(context)
    
    fun getUserName(): String? = userPreferences.getUserName()
    fun getUserEmail(): String? = userPreferences.getUserEmail()
    
    fun updateUser(name: String, email: String, currentPassword: String, newPassword: String): Boolean {
        val storedPassword = userPreferences.getUserPassword()
        return if (currentPassword.isEmpty() && newPassword.isEmpty()) {
            // Solo actualizar nombre/correo, mantener la contraseña actual
            if (storedPassword != null) {
                userPreferences.saveUser(email, name, storedPassword)
                true
            } else {
                false
            }
        } else {
            // Validar contraseña y actualizar todo
            if (storedPassword == currentPassword) {
                userPreferences.saveUser(email, name, newPassword)
                true
            } else {
                false
            }
        }
    }
    
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ConfigViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ConfigViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 