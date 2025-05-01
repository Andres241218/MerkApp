package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.CircleShape
import androidx.navigation.NavHostController

private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401

data class ProductState(
    val isFound: Boolean = false,
    val isNotFound: Boolean = false
)

@Composable
fun ListScreen(navController: NavHostController) {
    var selectedItems by remember { mutableStateOf(emptyMap<String, Pair<Boolean, String>>()) }
    var productStates by remember { mutableStateOf(mutableMapOf<String, ProductState>()) }

    LaunchedEffect(Unit) {
        try {
            val items = navController.previousBackStackEntry?.savedStateHandle?.get<Map<String, Pair<Boolean, String>>>("selectedItems")
            if (items != null) {
                selectedItems = items.filter { it.value.first }
                productStates = items.filter { it.value.first }
                    .mapValues { ProductState() }
                    .toMutableMap()
            }
        } catch (e: Exception) {
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
                selectedItems.forEach { (product, pair) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD4A76A)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = product,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = TextColor
                                )
                                Text(
                                    text = "Cantidad: ${pair.second}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = TextColor
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        productStates = productStates.toMutableMap().apply {
                                            val currentState = get(product) ?: ProductState()
                                            put(product, currentState.copy(
                                                isFound = !currentState.isFound,
                                                isNotFound = false
                                            ))
                                        }
                                    }
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (productStates[product]?.isFound == true)
                                            Color.Green.copy(alpha = 0.2f) else Color.Transparent,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Producto encontrado",
                                            tint = if (productStates[product]?.isFound == true)
                                                Color.Green else Color.Gray,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        productStates = productStates.toMutableMap().apply {
                                            val currentState = get(product) ?: ProductState()
                                            put(product, currentState.copy(
                                                isFound = false,
                                                isNotFound = !currentState.isNotFound
                                            ))
                                        }
                                    }
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = if (productStates[product]?.isNotFound == true)
                                            Color.Red.copy(alpha = 0.2f) else Color.Transparent,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Producto no encontrado",
                                            tint = if (productStates[product]?.isNotFound == true)
                                                Color.Red else Color.Gray,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigateUp() },
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
