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
import com.example.merkapp.ui.components.BottomNavBar
import com.example.merkapp.ui.viewmodels.UserViewModel
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    themeViewModel: ThemeViewModel
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
    val BackgroundColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF252440) else Color(0xFFDEB887) }
    val ButtonColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF2F2C78) else Color(0xFFCE8540) }
    val TextColor = remember(isDarkMode) { if (isDarkMode) Color.White else Color(0xFF314401) }
    val PanelColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF312C9B) else Color(0xFFDAA51E) }
    val CardColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF312C9B) else Color(0xFFD4A76A) }
    val ProductPanelColor = CardColor
    val logoRes = remember(isDarkMode) { if (isDarkMode) R.drawable.icw_logo else R.drawable.logo }
    val infoIcon = if (isDarkMode) R.drawable.icw_info else R.drawable.ic_info

    LaunchedEffect(Unit) {
        userViewModel.refreshUser()
    }
    var showInstructions by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val uiState by userViewModel.uiState.collectAsStateWithLifecycle()

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
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .padding(bottom = 80.dp) // Espacio para el menú de navegación
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
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
                            painter = painterResource(id = logoRes),
                            contentDescription = "MerkApp Logo",
                            modifier = Modifier
                                .size(120.dp) // Aumentado el tamaño del logo
                                .padding(bottom = 16.dp)
                        )

                        Text(
                            "¡Hola ${uiState.userName}!",
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
                                painter = painterResource(id = infoIcon),
                                contentDescription = "Instrucciones",
                                tint = TextColor,
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
                                                    Image(
                                                        painter = painterResource(id = getProductIconResource(product, themeViewModel)),
                                                        contentDescription = null,
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
                                                            productSelections[product] = (it.isNotBlank() to it)
                                                        },
                                                        label = {
                                                            Text(
                                                                "Cantidad",
                                                                color = TextColor,
                                                                fontWeight = FontWeight.SemiBold
                                                            )
                                                        },
                                                        modifier = Modifier.width(120.dp),
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
                                it.value.first // Solo productos con cantidad ingresada
                            }
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "selectedItems",
                                selectedItems
                            )
                            navController.navigate("list")
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

                Spacer(modifier = Modifier.height(32.dp)) // Espacio extra al final
            }

            // El menú de navegación siempre estará en la parte inferior
            BottomNavBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter),
                themeViewModel = themeViewModel
            )
        }
    }
}

private fun getProductIconResource(product: String, themeViewModel: ThemeViewModel): Int {
    val prefix = themeViewModel.getIconPrefix()
    return when (product.lowercase()) {
        // Proteínas
        "carne" -> getResourceId("${prefix}carne")
        "pollo" -> getResourceId("${prefix}pollo")
        "pescado" -> getResourceId("${prefix}pescado")
        "huevos" -> getResourceId("${prefix}huevos")

        // Víveres
        "avena" -> getResourceId("${prefix}avena")
        "azúcar" -> getResourceId("${prefix}azucar")
        "sal" -> getResourceId("${prefix}sal")
        "maíz" -> getResourceId("${prefix}maiz")
        "aceite" -> getResourceId("${prefix}aceite")
        "te" -> getResourceId("${prefix}te")
        "cafe" -> getResourceId("${prefix}cafe")
        "galletas" -> getResourceId("${prefix}galletas")
        "tostadas" -> getResourceId("${prefix}tostadas")

        // Frutas y verduras
        "pera" -> getResourceId("${prefix}pera")
        "piña" -> getResourceId("${prefix}pina")
        "banano" -> getResourceId("${prefix}banano")
        "arándanos" -> getResourceId("${prefix}arandanos")
        "sandia" -> getResourceId("${prefix}sandia")
        "mango" -> getResourceId("${prefix}mango")
        "uvas" -> getResourceId("${prefix}uvas")
        "manzanas" -> getResourceId("${prefix}manzana")
        "espinacas" -> getResourceId("${prefix}espinacas")
        "brócoli" -> getResourceId("${prefix}brocoli")
        "zanahoria" -> getResourceId("${prefix}zanahoria")
        "lechuga" -> getResourceId("${prefix}lechuga")
        "tomate" -> getResourceId("${prefix}tomate")
        "apio" -> getResourceId("${prefix}apio")
        "pepino" -> getResourceId("${prefix}pepino")
        "ahuyama" -> getResourceId("${prefix}ahuyama")

        // Aseo
        "escoba" -> getResourceId("${prefix}escoba")
        "recogedor" -> getResourceId("${prefix}recogedor")
        "esponjas" -> getResourceId("${prefix}esponjas")
        "guantes" -> getResourceId("${prefix}guantes")
        "limpia vidrios" -> getResourceId("${prefix}limpiavidrios")
        "trapeador" -> getResourceId("${prefix}trapeador")

        // Lácteos
        "leche" -> getResourceId("${prefix}leche")
        "queso" -> getResourceId("${prefix}queso")
        "yogurt" -> getResourceId("${prefix}yogurt")
        "mantequilla" -> getResourceId("${prefix}mantequilla")
        "crema de leche" -> getResourceId("${prefix}crema_leche")
        "kumis" -> getResourceId("${prefix}kumis")

        // Ícono por defecto
        else -> getResourceId("${prefix}producto_default")
    }
}

private fun getResourceId(name: String): Int {
    return try {
        val field = R.drawable::class.java.getDeclaredField(name)
        field.getInt(null)
    } catch (e: Exception) {
        R.drawable.ic_producto_default
    }
}
