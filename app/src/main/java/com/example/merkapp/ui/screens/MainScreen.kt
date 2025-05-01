package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.merkapp.R

// Definición de colores personalizados
private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val PanelColor = Color(0xFFDAA51E)      // #DAA51E
private val ProductPanelColor = Color(0xFFD4A76A) // Un tono más oscuro que BackgroundColor

// Definición de íconos para productos
private fun getProductIcon(product: String): ImageVector {
    return when (product.lowercase()) {
        // Proteínas
        "carne" -> Icons.Outlined.Restaurant
        "pollo" -> Icons.Outlined.Restaurant
        "pescado" -> Icons.Outlined.SetMeal
        "huevos" -> Icons.Outlined.EggAlt

        // Víveres
        "avena" -> Icons.Outlined.RiceBowl
        "azúcar" -> Icons.Outlined.Coffee
        "sal" -> Icons.Outlined.Kitchen
        "maíz" -> Icons.Outlined.Grass
        "aceite" -> Icons.Outlined.WaterDrop
        "te" -> Icons.Outlined.EmojiFoodBeverage
        "cafe" -> Icons.Outlined.Coffee
        "galletas" -> Icons.Outlined.Cookie
        "tostadas" -> Icons.Outlined.BakeryDining

        // Frutas y verduras
        "pera", "manzanas" -> Icons.Outlined.Apple
        "piña", "sandia", "mango" -> Icons.Outlined.Spa
        "banano", "uvas", "arándanos" -> Icons.Outlined.Apple
        "espinacas", "brócoli", "lechuga", "apio" -> Icons.Outlined.Grass
        "zanahoria", "tomate", "pepino", "ahuyama" -> Icons.Outlined.Eco

        // Aseo
        "escoba" -> Icons.Outlined.CleaningServices
        "recogedor" -> Icons.Outlined.CleaningServices
        "esponjas" -> Icons.Outlined.Wash
        "guantes" -> Icons.Outlined.CleanHands
        "limpia vidrios" -> Icons.Outlined.CleaningServices
        "trapeador" -> Icons.Outlined.CleaningServices

        // Lácteos
        "leche" -> Icons.Outlined.LocalDrink
        "queso" -> Icons.Outlined.LocalPizza
        "yogurt" -> Icons.Outlined.LocalDrink
        "mantequilla" -> Icons.Outlined.Kitchen
        "crema de leche" -> Icons.Outlined.LocalDrink
        "kumis" -> Icons.Outlined.LocalDrink

        // Ícono por defecto
        else -> Icons.Outlined.ShoppingCart
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, userName: String = "Cliente") {
    var showInstructions by remember { mutableStateOf(false) }

    val sections = listOf(
        "Proteína" to listOf("Carne", "Pollo", "Pescado", "Huevos"),
        "Víveres" to listOf(
            "Avena",
            "Azúcar",
            "Sal",
            "Maíz",
            "Aceite",
            "Te",
            "Cafe",
            "Galletas",
            "Tostadas"
        ),
        "Frutas y verduras" to listOf(
            "Pera",
            "Piña",
            "Banano",
            "Arándanos",
            "Sandia",
            "Mango",
            "Uvas",
            "Manzanas",
            "Espinacas",
            "Brócoli",
            "Zanahoria",
            "Lechuga",
            "Tomate",
            "Apio",
            "Pepino",
            "Ahuyama"
        ),
        "Aseo" to listOf(
            "Escoba",
            "Recogedor",
            "Esponjas",
            "Guantes",
            "Limpia vidrios",
            "Trapeador"
        ),
        "Lácteos" to listOf(
            "Leche",
            "Queso",
            "Yogurt",
            "Mantequilla",
            "Crema de leche",
            "Kumis"
        )
    )

    val productSelections = remember { mutableStateMapOf<String, Pair<Boolean, String>>() }
    val expandedSections = remember { mutableStateMapOf<String, Boolean>() }

    // Dialog de instrucciones
    if (showInstructions) {
        Dialog(onDismissRequest = { showInstructions = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = BackgroundColor
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Instrucciones de Uso",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        "1. Toca cada panel para ver los productos disponibles\n\n" +
                                "2. Selecciona los productos que desees marcando la casilla\n\n" +
                                "3. Ingresa la cantidad deseada para cada producto\n\n" +
                                "4. Presiona 'Mi lista' para ver tu selección final",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = TextColor,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Button(
                        onClick = { showInstructions = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        )
                    ) {
                        Text(
                            "Entendido",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "MerkApp Logo",
                        modifier = Modifier
                            .size(120.dp) // Aumentado el tamaño del logo
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        "Hola $userName",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                }

                // Botón de información en la esquina superior derecha
                IconButton(
                    onClick = { showInstructions = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
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
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Resto del contenido permanece igual
            sections.forEach { (section, products) ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        expandedSections[section] = !(expandedSections[section] ?: false)
                    },
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = PanelColor
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = PanelColor,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = section,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = TextColor,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        if (expandedSections[section] == true) {
                            Surface(
                                color = BackgroundColor,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    products.forEach { product ->
                                        val selection = productSelections[product] ?: (false to "")
                                        var isChecked by remember { mutableStateOf(selection.first) }
                                        var quantity by remember { mutableStateOf(selection.second) }

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = ProductPanelColor
                                            ),
                                            elevation = CardDefaults.cardElevation(
                                                defaultElevation = 2.dp
                                            )
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Checkbox(
                                                    checked = isChecked,
                                                    onCheckedChange = {
                                                        isChecked = it
                                                        productSelections[product] = isChecked to quantity
                                                    },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = ButtonColor,
                                                        uncheckedColor = TextColor
                                                    )
                                                )
                                                
                                                Icon(
                                                    imageVector = getProductIcon(product),
                                                    contentDescription = null,
                                                    tint = TextColor,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .padding(end = 8.dp)
                                                )
                                                
                                                Text(
                                                    text = product,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(horizontal = 8.dp),
                                                    color = TextColor,
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                )
                                                OutlinedTextField(
                                                    value = quantity,
                                                    onValueChange = {
                                                        quantity = it
                                                        productSelections[product] = isChecked to quantity
                                                    },
                                                    label = {
                                                        Text(
                                                            "Cantidad",
                                                            color = TextColor,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    },
                                                    modifier = Modifier.width(100.dp),
                                                    singleLine = true,
                                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                                        focusedTextColor = TextColor,
                                                        unfocusedTextColor = TextColor,
                                                        focusedBorderColor = ButtonColor,
                                                        unfocusedBorderColor = TextColor,
                                                        focusedLabelColor = ButtonColor,
                                                        unfocusedLabelColor = TextColor
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    try {
                        val selectedItems = productSelections.toMap().filter {
                            it.value.first && it.value.second.isNotBlank()
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "selectedItems",
                            selectedItems
                        )
                        if (selectedItems.isNotEmpty()) {
                            navController.navigate("list")
                        }
                    } catch (e: Exception) {
                        // Manejo de errores
                    }
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
                    "Mi lista",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
