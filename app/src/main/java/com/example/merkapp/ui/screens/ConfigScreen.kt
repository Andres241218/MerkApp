package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.merkapp.R
import com.example.merkapp.ui.components.BottomNavBar
import com.example.merkapp.ui.viewmodels.ConfigViewModel

// Definición de colores personalizados
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: ConfigViewModel = viewModel(factory = ConfigViewModel.Factory(context))
    
    var initialEmail by remember { mutableStateOf(viewModel.getUserEmail() ?: "") }
    var name by remember { mutableStateOf(viewModel.getUserName() ?: "") }
    var email by remember { mutableStateOf(initialEmail) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showInfoDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val emailChanged = email != initialEmail
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo y título
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Configuración",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextColor
                    )
                    IconButton(
                        onClick = { showInfoDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Información",
                            tint = TextColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp)) // Espacio más grande entre título y campos
            }
            // Contenido scrollable con padding inferior para el BottomNavBar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 80.dp, top = 200.dp) // top más grande para dejar espacio
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ButtonColor,
                        unfocusedBorderColor = ButtonColor
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ButtonColor,
                        unfocusedBorderColor = ButtonColor
                    )
                )
                if (emailChanged) {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Contraseña actual") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ButtonColor,
                            unfocusedBorderColor = ButtonColor
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ButtonColor,
                            unfocusedBorderColor = ButtonColor
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar nueva contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ButtonColor,
                            unfocusedBorderColor = ButtonColor
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (emailChanged) {
                            when {
                                newPassword != confirmPassword -> {
                                    errorMessage = "Las contraseñas no coinciden"
                                    showErrorDialog = true
                                }
                                newPassword.isEmpty() -> {
                                    errorMessage = "La nueva contraseña no puede estar vacía"
                                    showErrorDialog = true
                                }
                                else -> {
                                    if (viewModel.updateUser(name, email, currentPassword, newPassword)) {
                                        showSuccessDialog = true
                                        currentPassword = ""
                                        newPassword = ""
                                        confirmPassword = ""
                                    } else {
                                        errorMessage = "Contraseña actual incorrecta"
                                        showErrorDialog = true
                                    }
                                }
                            }
                        } else {
                            // Solo actualiza nombre y correo
                            if (viewModel.updateUser(name, email, "", "")) {
                                showSuccessDialog = true
                            } else {
                                errorMessage = "Error al actualizar los datos"
                                showErrorDialog = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor
                    )
                ) {
                    Text("Aceptar cambios")
                }
            }
            BottomNavBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
    if (showInfoDialog) {
        AlertDialog(
            containerColor = BackgroundColor,
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Información", color = TextColor) },
            text = { Text("En esta pantalla puedes actualizar tu información personal y cambiar tu contraseña. Asegúrate de que la nueva contraseña sea segura y fácil de recordar.", color = TextColor) },
            confirmButton = {
                Button(
                    onClick = { showInfoDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text("Entendido", color = TextColor)
                }
            }
        )
    }
    if (showSuccessDialog) {
        AlertDialog(
            containerColor = BackgroundColor,
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Éxito", color = TextColor) },
            text = { Text("Tus datos han sido actualizados correctamente.", color = TextColor) },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text("Aceptar", color = TextColor)
                }
            }
        )
    }
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
} 