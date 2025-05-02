package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
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
import com.example.merkapp.ui.components.BottomNavBar
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import androidx.compose.foundation.clickable

private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val CardColor = Color(0xFFD4A76A)       // #D4A76A
private val PanelColor = Color(0xFFDAA51E)      // #DAA51E

data class ProductState(
    val isFound: Boolean = false,
    val isNotFound: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavHostController,
    shoppingListViewModel: ShoppingListViewModel
) {
    var selectedItems by remember { mutableStateOf<Map<String, Pair<Boolean, String>>>(emptyMap()) }
    var productStates by remember { mutableStateOf<Map<String, ProductState>>(emptyMap()) }
    var showInstructions by remember { mutableStateOf(false) }
    var showMissingProducts by remember { mutableStateOf(false) }
    var showCompletionDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showAddProductDialog by remember { mutableStateOf(false) }
    var newProductName by remember { mutableStateOf("") }
    var newProductQuantity by remember { mutableStateOf("") }
    var showAddSuccessDialog by remember { mutableStateOf(false) }
    var showEditProductDialog by remember { mutableStateOf(false) }
    var showEditQuantityDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf("") }
    var newEditQuantity by remember { mutableStateOf("") }
    var showEditSuccessDialog by remember { mutableStateOf(false) }
    var showDeleteProductDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf("") }
    var showDeleteSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val items = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Map<String, Pair<Boolean, String>>>("selectedItems")
        if (items != null) {
            selectedItems = items.filter { it.value.first }
            productStates = items.filter { it.value.first }
                .mapValues { ProductState() }
        }
    }

    // Diálogo de productos faltantes
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

                    LazyColumn {
                        items(missingProducts.toList()) { (product, pair) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "• $product",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextColor,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Cantidad: ${pair.second}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showMissingProducts = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        )
                    ) {
                        Text("Entendido")
                    }
                }
            }
        }
    }

    // Diálogo de compra completada
    if (showCompletionDialog) {
        Dialog(onDismissRequest = { /* No cerrar al tocar fuera */ }) {
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
                    val notFoundProducts = productStates.filter { it.value.isNotFound }.keys.toList()
                    
                    if (notFoundProducts.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = TextColor,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "¡Genial! Encontraste todos los productos.",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Los siguientes productos no se encontraron:",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        notFoundProducts.forEach { product ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = TextColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = product,
                                    color = TextColor
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = {
                            // Guardar la lista completada
                            shoppingListViewModel.saveNewList(selectedItems, productStates)
                            navController.navigate("main") {
                                popUpTo("main") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Regresar al inicio")
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
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo centrado y menú hamburguesa
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = TextColor
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                    )
                    IconButton(
                        onClick = { showInstructions = true },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Instrucciones",
                            tint = TextColor
                        )
                    }
                }

                Text(
                    text = "Mi Lista",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (selectedItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay productos seleccionados.",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(selectedItems.toList()) { (product, pair) ->
                            val quantity = pair.second
                            val productState = productStates[product] ?: ProductState()
                            
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
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = product,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextColor
                                        )
                                        Text(
                                            text = "Cantidad: $quantity",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextColor
                                        )
                                    }
                                    
                                    Row {
                                        val checkButtonColor by animateColorAsState(
                                            targetValue = if (productState.isFound) Color(0xFF90EE90) else Color.Transparent,
                                            animationSpec = tween(durationMillis = 300),
                                            label = "checkButtonColor"
                                        )

                                        val xButtonColor by animateColorAsState(
                                            targetValue = if (productState.isNotFound) Color(0xFFFF6961) else Color.Transparent,
                                            animationSpec = tween(durationMillis = 300),
                                            label = "xButtonColor"
                                        )

                                        IconButton(
                                            onClick = {
                                                productStates = productStates.toMutableMap().apply {
                                                    put(product, ProductState(isFound = true, isNotFound = false))
                                                }
                                            },
                                            modifier = Modifier
                                                .background(
                                                    color = checkButtonColor,
                                                    shape = CircleShape
                                                )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Encontrado",
                                                tint = if (productState.isFound) Color.White else TextColor.copy(alpha = 0.6f)
                                            )
                                        }
                                        
                                        Spacer(modifier = Modifier.width(8.dp))
                                        
                                        IconButton(
                                            onClick = {
                                                productStates = productStates.toMutableMap().apply {
                                                    put(product, ProductState(isFound = false, isNotFound = true))
                                                }
                                            },
                                            modifier = Modifier
                                                .background(
                                                    color = xButtonColor,
                                                    shape = CircleShape
                                                )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "No encontrado",
                                                tint = if (productState.isNotFound) Color.White else TextColor.copy(alpha = 0.6f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showCompletionDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Finalizar compra",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

            BottomNavBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            // Menú lateral hamburguesa
            if (showMenu) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000)) // Fondo semitransparente para efecto modal
                        .clickable(onClick = { showMenu = false })
                ) {}
                Column(
                    modifier = Modifier
                        .width(260.dp)
                        .fillMaxHeight()
                        .background(BackgroundColor)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showAddProductDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("Agregar un producto", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showEditProductDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("Editar la cantidad de un producto", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showDeleteProductDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("Eliminar un producto", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { showMenu = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cerrar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            // Lightbox para agregar producto
            if (showAddProductDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                        .clickable(onClick = { showAddProductDialog = false })
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = newProductName,
                        onValueChange = { newProductName = it },
                        label = { Text("Nombre del producto", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PanelColor,
                            unfocusedBorderColor = PanelColor
                        )
                    )
                    OutlinedTextField(
                        value = newProductQuantity,
                        onValueChange = { newProductQuantity = it },
                        label = { Text("Cantidad", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PanelColor,
                            unfocusedBorderColor = PanelColor
                        )
                    )
                    Button(
                        onClick = {
                            if (newProductName.isNotBlank() && newProductQuantity.isNotBlank()) {
                                selectedItems = selectedItems + (newProductName to (true to newProductQuantity))
                                productStates = productStates + (newProductName to ProductState())
                                showAddProductDialog = false
                                showMenu = false
                                newProductName = ""
                                newProductQuantity = ""
                                showAddSuccessDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("Agregar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            showAddProductDialog = false
                            newProductName = ""
                            newProductQuantity = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            // Lightbox para elegir producto a editar
            if (showEditProductDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                        .clickable(onClick = { showEditProductDialog = false })
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Elige el producto a editar", fontWeight = FontWeight.Bold, color = TextColor, modifier = Modifier.padding(bottom = 16.dp))
                    selectedItems.keys.forEach { product ->
                        Button(
                            onClick = {
                                productToEdit = product
                                newEditQuantity = selectedItems[product]?.second ?: ""
                                showEditProductDialog = false
                                showEditQuantityDialog = true
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CardColor)
                        ) {
                            Text(product, color = TextColor, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            showEditProductDialog = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            // Lightbox para editar cantidad del producto seleccionado
            if (showEditQuantityDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                        .clickable(onClick = { showEditQuantityDialog = false })
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = productToEdit,
                        onValueChange = {},
                        label = { Text("Producto elegido", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        enabled = false,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PanelColor,
                            unfocusedBorderColor = PanelColor
                        )
                    )
                    OutlinedTextField(
                        value = newEditQuantity,
                        onValueChange = { newEditQuantity = it },
                        label = { Text("Cantidad nueva", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PanelColor,
                            unfocusedBorderColor = PanelColor
                        )
                    )
                    Button(
                        onClick = {
                            if (productToEdit.isNotBlank() && newEditQuantity.isNotBlank()) {
                                selectedItems = selectedItems.toMutableMap().apply {
                                    put(productToEdit, (true to newEditQuantity))
                                }
                                showEditQuantityDialog = false
                                productToEdit = ""
                                newEditQuantity = ""
                                showEditSuccessDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("Editar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            showEditQuantityDialog = false
                            productToEdit = ""
                            newEditQuantity = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            // Lightbox para elegir producto a eliminar
            if (showDeleteProductDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                        .clickable(onClick = { showDeleteProductDialog = false })
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Elige el producto a eliminar", fontWeight = FontWeight.Bold, color = TextColor, modifier = Modifier.padding(bottom = 16.dp))
                    selectedItems.keys.forEach { product ->
                        Button(
                            onClick = {
                                productToDelete = product
                                showDeleteProductDialog = false
                                showDeleteConfirmDialog = true
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CardColor)
                        ) {
                            Text(product, color = TextColor, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            showDeleteProductDialog = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            // Lightbox de confirmación para eliminar producto
            if (showDeleteConfirmDialog) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000))
                        .clickable(onClick = { showDeleteConfirmDialog = false })
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("¿Estas seguro de eliminar el producto?", fontWeight = FontWeight.Bold, color = TextColor, modifier = Modifier.padding(bottom = 16.dp), textAlign = TextAlign.Center)
                    OutlinedTextField(
                        value = productToDelete,
                        onValueChange = {},
                        label = { Text("Producto elegido", fontWeight = FontWeight.Bold) },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        enabled = false,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = PanelColor,
                            unfocusedBorderColor = PanelColor
                        )
                    )
                    Button(
                        onClick = {
                            if (productToDelete.isNotBlank()) {
                                selectedItems = selectedItems.toMutableMap().apply { remove(productToDelete) }
                                productStates = productStates.toMutableMap().apply { remove(productToDelete) }
                                showDeleteConfirmDialog = false
                                productToDelete = ""
                                showDeleteSuccessDialog = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PanelColor)
                    ) {
                        Text("eliminar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            showDeleteConfirmDialog = false
                            productToDelete = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Diálogo de instrucciones
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
                        text = "1. Marca los productos que hayas encontrado con el ícono de check (✓)\n\n" +
                               "2. Marca los productos que no hayas encontrado con el ícono de X\n\n" +
                               "3. Cuando termines, presiona el botón 'Completado'",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showInstructions = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        )
                    ) {
                        Text("Entendido")
                    }
                }
            }
        }
    }

    // Feedback de producto agregado con éxito
    if (showAddSuccessDialog) {
        Dialog(onDismissRequest = { showAddSuccessDialog = false }) {
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
                        text = "Producto agregado a la lista con éxito",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showAddSuccessDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Aceptar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Feedback de producto editado con éxito
    if (showEditSuccessDialog) {
        Dialog(onDismissRequest = { showEditSuccessDialog = false }) {
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
                        text = "Producto editado con éxito",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showEditSuccessDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Aceptar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Feedback de producto eliminado con éxito
    if (showDeleteSuccessDialog) {
        Dialog(onDismissRequest = { showDeleteSuccessDialog = false }) {
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
                        text = "Producto eliminado con éxito",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showDeleteSuccessDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
                    ) {
                        Text("Aceptar", color = TextColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
