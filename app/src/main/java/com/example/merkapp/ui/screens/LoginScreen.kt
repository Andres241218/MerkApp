package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.merkapp.R
import com.example.merkapp.ui.viewmodels.UserViewModel

<<<<<<< HEAD
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val PanelColor = Color(0xFFDAA51E)      // #DAA51E

@OptIn(ExperimentalMaterial3Api::class)
=======
// Importar el ViewModel y modelo (si están en el mismo archivo, omite esto)
import com.example.merkapp.ui.screens.UserViewModel

>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by userViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
<<<<<<< HEAD
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
=======
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
>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PanelColor,
                    unfocusedContainerColor = PanelColor,
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = PanelColor,
                    unfocusedContainerColor = PanelColor,
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { userViewModel.login(email, password) },
                enabled = !uiState.isLoading && email.isNotBlank() && password.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = TextColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = TextColor,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Iniciar sesión",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate("register") }
            ) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = TextColor
                )
            }
        }
    }
}
