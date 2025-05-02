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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.merkapp.R
import com.example.merkapp.ui.viewmodels.UserViewModel

<<<<<<< HEAD
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401

@OptIn(ExperimentalMaterial3Api::class)
=======
// Modelo de datos
data class User(
    val name: String,
    val email: String,
    val password: String
)

// ViewModel que guarda los usuarios
class UserViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    fun addUser(user: User) {
        _users.add(user)
    }
}

>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
@Composable
fun RegisterScreen(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
<<<<<<< HEAD
=======
    var name by remember { mutableStateOf("") }
>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }
    
    val uiState by userViewModel.uiState.collectAsState()

<<<<<<< HEAD
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            navController.navigate("main") {
=======
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registrarse", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
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
                    name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                        "Todos los campos son obligatorios"
                    password != confirmPassword ->
                        "Las contraseñas no coinciden"
                    else -> null
                }

                if (errorMessage == null) {
                    userViewModel.addUser(User(name, email, password))
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("login") {
>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
                popUpTo("register") { inclusive = true }
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.error != null || localError != null) {
                Text(
                    text = localError ?: uiState.error!!,
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
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor
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
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ButtonColor,
                    unfocusedBorderColor = TextColor,
                    focusedLabelColor = ButtonColor,
                    unfocusedLabelColor = TextColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    localError = when {
                        email.isBlank() || name.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                            "Por favor completa todos los campos"
                        !email.contains("@") ->
                            "Por favor ingresa un correo válido"
                        password != confirmPassword ->
                            "Las contraseñas no coinciden"
                        password.length < 6 ->
                            "La contraseña debe tener al menos 6 caracteres"
                        else -> null
                    }

                    if (localError == null) {
                        userViewModel.register(email, name, password)
                    }
                },
                enabled = !uiState.isLoading,
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
                        text = "Registrarse",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigateUp() }
            ) {
                Text(
                    text = "¿Ya tienes cuenta? Inicia sesión",
                    color = TextColor
                )
            }
        }
    }
}
