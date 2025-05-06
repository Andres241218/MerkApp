package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
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
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.runBlocking
import androidx.compose.foundation.clickable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

// Definición de colores personalizados
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val CardColor = Color(0xFFD4A76A)
private val DarkBackgroundColor = Color(0xFF252440)
private val DarkButtonColor = Color(0xFF2F2C78)
private val DarkPanelColor = Color(0xFF312C9B)
private val DarkTextColor = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    themeViewModel: ThemeViewModel
) {
    val context = LocalContext.current
    val viewModel: ConfigViewModel = viewModel(factory = ConfigViewModel.Factory(context))
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
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
    var rememberMe by remember { mutableStateOf(false) }
    val EMAIL_KEY = stringPreferencesKey("saved_email")
    val PASSWORD_KEY = stringPreferencesKey("saved_password")

    val backgroundColor = if (isDarkMode) DarkBackgroundColor else BackgroundColor
    val buttonColor = if (isDarkMode) DarkButtonColor else ButtonColor
    val textColorValue = if (isDarkMode) DarkTextColor else TextColor
    val panelColor = if (isDarkMode) DarkPanelColor else CardColor
    val logoRes = if (isDarkMode) R.drawable.icw_logo else R.drawable.logo
    val infoIcon = if (isDarkMode) R.drawable.icw_info else R.drawable.ic_info

    // Autollenar contraseña actual si cambia el correo
    LaunchedEffect(emailChanged) {
        if (emailChanged) {
            currentPassword = viewModel.getUserPassword() ?: ""
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
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
                    painter = painterResource(id = logoRes),
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
                        color = textColorValue
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { themeViewModel.toggleDarkMode() }
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = buttonColor,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = if (isDarkMode) R.drawable.icw_white else R.drawable.ic_dark),
                                    contentDescription = if (isDarkMode) "Light Mode" else "Dark Mode",
                                    tint = textColorValue,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(24.dp)
                                )
                            }
                        }
                        IconButton(
                            onClick = { showInfoDialog = true }
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = buttonColor,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = infoIcon),
                                    contentDescription = "Información",
                                    tint = textColorValue,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { themeViewModel.toggleDarkMode() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text(
                        if (isDarkMode) "Cambiar a Light Mode" else "Cambiar a Dark Mode",
                        color = textColorValue
                    )
                }
            }
            // Contenido scrollable con padding inferior para el BottomNavBar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 80.dp, top = 200.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre", color = if (isDarkMode) DarkTextColor else Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = panelColor,
                        focusedBorderColor = buttonColor,
                        unfocusedBorderColor = buttonColor,
                        focusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                        unfocusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                        focusedTextColor = if (isDarkMode) DarkTextColor else Color.Black,
                        unfocusedTextColor = if (isDarkMode) DarkTextColor else Color.Black
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico", color = if (isDarkMode) DarkTextColor else Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = panelColor,
                        focusedBorderColor = buttonColor,
                        unfocusedBorderColor = buttonColor,
                        focusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                        unfocusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                        focusedTextColor = if (isDarkMode) DarkTextColor else Color.Black,
                        unfocusedTextColor = if (isDarkMode) DarkTextColor else Color.Black
                    )
                )
                if (emailChanged) {
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = { Text("Contraseña actual", color = if (isDarkMode) DarkTextColor else Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = panelColor,
                            focusedBorderColor = buttonColor,
                            unfocusedBorderColor = buttonColor,
                            focusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            focusedTextColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedTextColor = if (isDarkMode) DarkTextColor else Color.Black
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva contraseña", color = if (isDarkMode) DarkTextColor else Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = panelColor,
                            focusedBorderColor = buttonColor,
                            unfocusedBorderColor = buttonColor,
                            focusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            focusedTextColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedTextColor = if (isDarkMode) DarkTextColor else Color.Black
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar nueva contraseña", color = if (isDarkMode) DarkTextColor else Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = panelColor,
                            focusedBorderColor = buttonColor,
                            unfocusedBorderColor = buttonColor,
                            focusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedLabelColor = if (isDarkMode) DarkTextColor else Color.Black,
                            focusedTextColor = if (isDarkMode) DarkTextColor else Color.Black,
                            unfocusedTextColor = if (isDarkMode) DarkTextColor else Color.Black
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
                // Checkbox Recordarme más tarde
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = buttonColor,
                            uncheckedColor = textColorValue
                        )
                    )
                    Text(
                        text = "Recordarme más tarde",
                        color = textColorValue,
                        modifier = Modifier.clickable { rememberMe = !rememberMe }
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
                                        if (rememberMe) {
                                            runBlocking {
                                                userViewModel.dataStore.edit { preferences ->
                                                    preferences[EMAIL_KEY] = email
                                                    preferences[PASSWORD_KEY] = newPassword
                                                }
                                            }
                                        }
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
                                if (rememberMe) {
                                    runBlocking {
                                        userViewModel.dataStore.edit { preferences ->
                                            preferences[EMAIL_KEY] = email
                                            preferences[PASSWORD_KEY] = viewModel.getUserPassword() ?: ""
                                        }
                                    }
                                }
                                showSuccessDialog = true
                            } else {
                                errorMessage = "Error al actualizar los datos"
                                showErrorDialog = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    )
                ) {
                    Text("Aceptar cambios", color = textColorValue)
                }
            }
            BottomNavBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter),
                themeViewModel = themeViewModel
            )
        }
    }
    if (showInfoDialog) {
        AlertDialog(
            containerColor = backgroundColor,
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Información", color = textColorValue) },
            text = { Text("En esta pantalla puedes actualizar tu información personal y cambiar tu contraseña. Asegúrate de que la nueva contraseña sea segura y fácil de recordar.", color = textColorValue) },
            confirmButton = {
                Button(
                    onClick = { showInfoDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("Entendido", color = textColorValue)
                }
            }
        )
    }
    if (showSuccessDialog) {
        AlertDialog(
            containerColor = backgroundColor,
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Éxito", color = textColorValue) },
            text = { Text("Tus datos han sido actualizados correctamente.", color = textColorValue) },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("Aceptar", color = textColorValue)
                }
            }
        )
    }
    if (showErrorDialog) {
        AlertDialog(
            containerColor = backgroundColor,
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error", color = textColorValue) },
            text = { Text(errorMessage, color = textColorValue) },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("Aceptar", color = textColorValue)
                }
            }
        )
    }
}

// Si hay un preview o llamada a ConfigScreen sin themeViewModel, elimínala o comenta la línea.
// @Preview
// @Composable
// fun ConfigScreenPreview() {
//     ConfigScreen(navController = ..., userViewModel = ..., themeViewModel = ...)
// } 