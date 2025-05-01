package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

// Importar el ViewModel y modelo (si están en el mismo archivo, omite esto)
import com.example.merkapp.ui.screens.UserViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = when {
                    email.isBlank() || password.isBlank() ->
                        "Por favor completa todos los campos"
                    else -> {
                        // Buscar el usuario en la lista
                        val user = userViewModel.users.find {
                            it.email == email && it.password == password
                        }
                        if (user != null) {
                            // Navegar si el usuario existe
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                            null // No hay error
                        } else {
                            "Correo o contraseña incorrectos"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("register") {
                popUpTo("login") { inclusive = false }
            }
        }) {
            Text("¿No tienes cuenta? Regístrate")
        }

        TextButton(onClick = {
            navController.navigate("forgotPassword") {
                popUpTo("login") { inclusive = false }
            }
        }) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}
