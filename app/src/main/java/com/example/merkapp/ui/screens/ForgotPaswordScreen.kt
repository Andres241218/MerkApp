package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import android.util.Patterns

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Recuperar contraseña", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
                showConfirmation = false
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    email.isBlank() -> errorMessage = "Ingresa tu correo"
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        errorMessage = "Correo no válido"
                    else -> {
                        // Aquí puedes conectar con Firebase, etc.
                        showConfirmation = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar instrucciones")
        }

        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Se enviaron las instrucciones a tu correo.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            navController.navigate("login") {
                popUpTo("forgotPassword") { inclusive = true }
            }
        }) {
            Text("Volver al inicio de sesión")
        }
    }
}
