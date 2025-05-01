package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.merkapp.R

private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val CardColor = Color(0xFFD4A76A)       // #D4A76A

data class ProductState(
    val isFound: Boolean = false,
    val isNotFound: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController) {
    var selectedItems by remember { mutableStateOf(emptyMap<String, Pair<Boolean, String>>()) }
    var productStates by remember { mutableStateOf(mutableMapOf<String, ProductState>()) }
    var showInstructions by remember { mutableStateOf(false) }
    var showMissingProducts by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val items = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Map<String, Pair<Boolean, String>>>("selectedItems")
        if (items != null) {
            selectedItems = items.filter { it.value.first }
            productStates = items.filter { it.value.first }
                .mapValues { ProductState() }
                .toMutableMap()
        }
    }

    // Dialog de productos faltantes
    if (showMissingProducts) {
        val missingProducts = selectedItems.filter { (product, _) ->
            productStates[product]?.isNotFound == true
        }

        Dialog(onDismissRequest = { showMissingProducts = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Productos no encontrados",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (missingProducts.isEmpty()) {
                        Text(
                            text = "¡Genial! Encontraste todos los productos.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColor,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Los siguientes productos no se encontraron:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextColor,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            missingProducts.forEach { (product, pair) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "• $product",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextColor
                                    )
                                    Text(
                                        text = "Cantidad: ${pair.second}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextColor
                                    )
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { 
                                showMissingProducts = false
                                navController.navigateUp()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ButtonColor,
                                contentColor = TextColor
                            )
                        ) {
                            Text("Regresar al inicio")
                        }
                    }
                }
            }
        }
    }

    if (showInstructions) {
        Dialog(onDismissRequest = { showInstructions = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Instrucciones",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "1. Marca ✓ si encontraste el producto.\n" +
                                "2. Marca ✗ si no encontraste el producto.\n" +
                                "3. Los productos sin marcar están pendientes por buscar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextColor,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showInstructions = false },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, contentColor = TextColor)
                    ) {
                        Text("Entendido")
                    }
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo de MerkApp",
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "Mi Lista",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                }
                IconButton(
                    onClick = { showInstructions = true },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = ButtonColor,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Instrucciones",
                            tint = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedItems.isEmpty()) {
                Text(
                    text = "No hay productos seleccionados.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                selectedItems.forEach { (product, pair) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = CardColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextColor
                                )
                                Text(
                                    text = "Cantidad: ${pair.second}",
                                    style = MaterialTheme.typography.bodyMedium,
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
                onClick = { showMissingProducts = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = TextColor
                )
            ) {
                Text(
                    "Finalizar compra",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
