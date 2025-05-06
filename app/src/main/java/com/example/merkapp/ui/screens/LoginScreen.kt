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
import androidx.navigation.NavHostController
import com.example.merkapp.R
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

private val EMAIL_KEY = stringPreferencesKey("saved_email")
private val PASSWORD_KEY = stringPreferencesKey("saved_password")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    themeViewModel: ThemeViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

    val BackgroundColor = if (isDarkMode) Color(0xFF252440) else Color(0xFFDEB887)
    val ButtonColor = if (isDarkMode) Color(0xFF2F2C78) else Color(0xFFCE8540)
    val TextColor = if (isDarkMode) Color.White else Color(0xFF314401)
    val CardColor = if (isDarkMode) Color(0xFF312C9B) else Color(0xFFD4A76A)

    val logoRes = if (isDarkMode) R.drawable.icw_logo else R.drawable.logo

    // Cargar credenciales guardadas al iniciar
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val savedEmail = userViewModel.dataStore.data.first()[EMAIL_KEY]
                val savedPassword = userViewModel.dataStore.data.first()[PASSWORD_KEY]
                if (savedEmail != null && savedPassword != null) {
                    email = savedEmail
                    password = savedPassword
                }
            } catch (e: Exception) {
                // No hay credenciales guardadas
            }
        }
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = logoRes),
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
                label = { Text("Correo electrónico", color = if (isDarkMode) Color.White else Color.Black) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardColor,
                    unfocusedContainerColor = CardColor,
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = if (isDarkMode) Color.White else Color.Black,
                    unfocusedLabelColor = if (isDarkMode) Color.White else Color.Black,
                    focusedTextColor = if (isDarkMode) Color.White else Color.Black,
                    unfocusedTextColor = if (isDarkMode) Color.White else Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = if (isDarkMode) Color.White else Color.Black) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardColor,
                    unfocusedContainerColor = CardColor,
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = if (isDarkMode) Color.White else Color.Black,
                    unfocusedLabelColor = if (isDarkMode) Color.White else Color.Black,
                    focusedTextColor = if (isDarkMode) Color.White else Color.Black,
                    unfocusedTextColor = if (isDarkMode) Color.White else Color.Black
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
