package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Usando los mismos colores que en MainScreen
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401

@Composable
fun ListScreen(navController: NavHostController) {
    var selectedItems by remember { mutableStateOf(emptyMap<String, Pair<Boolean, String>>()) }

    // Cargar los items seleccionados de forma segura
    LaunchedEffect(Unit) {
        try {
            val items = navController.previousBackStackEntry?.savedStateHandle?.get<Map<String, Pair<Boolean, String>>>("selectedItems")
            if (items != null) {
                selectedItems = items.filter { it.value.first }
            }
        } catch (e: Exception) {
            // Si hay algún error, mantenemos la lista vacía
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mi Lista",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = TextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedItems.isEmpty()) {
                Text(
                    text = "No hay productos seleccionados.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = TextColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                // Mostrar productos seleccionados
                selectedItems.forEach { (product, pair) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD4A76A) // ProductPanelColor
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = product,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = TextColor,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "Cantidad: ${pair.second}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = TextColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { 
                    navController.navigateUp()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = TextColor
                )
            ) {
                Text(
                    "Regresar",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}