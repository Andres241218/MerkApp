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
import com.example.merkapp.data.ShoppingList
import com.example.merkapp.ui.components.BottomNavBar
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListsScreen(
    navController: NavHostController,
    viewModel: ShoppingListViewModel,
    userViewModel: UserViewModel,
    themeViewModel: ThemeViewModel
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
    val BackgroundColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF252440) else Color(0xFFDEB887) }
    val ButtonColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF2F2C78) else Color(0xFFCE8540) }
    val TextColor = remember(isDarkMode) { if (isDarkMode) Color.White else Color(0xFF314401) }
    val CardColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF312C9B) else Color(0xFFD4A76A) }
    val logoRes = remember(isDarkMode) { if (isDarkMode) R.drawable.icw_logo else R.drawable.logo }
    val infoIcon = if (isDarkMode) R.drawable.icw_info else R.drawable.ic_info

    val lists by viewModel.shoppingLists.collectAsState()
    var selectedList by remember { mutableStateOf<ShoppingList?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showDeleteSuccessDialog by remember { mutableStateOf(false) }
    var showInstructions by remember { mutableStateOf(false) }

    LaunchedEffect(showDialog) {
        if (!showDialog) {
            selectedList = null
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = logoRes),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                    )
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

                Text(
                    text = "Mis Listas",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                if (lists.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay listas guardadas",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(lists.reversed()) { list ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = CardColor),
                                onClick = { 
                                    selectedList = list
                                    showDialog = true
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Lista del ${list.date}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextColor,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "${list.items.size} productos",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextColor
                                    )

                                    val foundItems = list.items.count { it.value.isFound }
                                    val notFoundItems = list.items.count { it.value.isNotFound }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color(0xFF90EE90),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = " $foundItems",
                                                color = TextColor,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null,
                                                tint = Color(0xFFFF6961),
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = " $notFoundItems",
                                                color = TextColor,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            BottomNavBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter),
                themeViewModel = themeViewModel
            )
        }
    }

    if (showDialog && selectedList != null) {
        Dialog(
            onDismissRequest = { 
                showDialog = false
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Detalles de la lista",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Esta es la lista que has seleccionado.\n\nPuedes eliminar la lista si ya no la necesitas.\n\nSi deseas volver a comprar los mismos productos, usa el botón 'Agregar'.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f, false)
                            .padding(vertical = 8.dp)
                    ) {
                        items(selectedList?.items?.toList() ?: emptyList()) { (product, item) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product,
                                        color = TextColor,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Cantidad: ${item.quantity}",
                                        color = TextColor,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    if (item.cost.isNotBlank()) {
                                        Text(
                                            text = "Costo: $${item.cost}",
                                            color = TextColor,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }

                                if (item.isFound) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color(0xFF90EE90)
                                    )
                                } else if (item.isNotFound) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color(0xFFFF6961)
                                    )
                                }
                            }
                        }
                    }
                    val totalCost = selectedList?.items?.filter { it.value.isFound }
                        ?.mapNotNull { it.value.cost.toDoubleOrNull() }
                        ?.sum() ?: 0.0
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Total gastado: $" + String.format("%.2f", totalCost),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { showDeleteConfirmDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6961)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Eliminar", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                // Pasar la lista seleccionada a la vista Mi lista
                                val itemsToSend = selectedList!!.items.mapValues { (k, v) -> true to v.quantity }
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedItems", itemsToSend)
                                showDialog = false
                                navController.navigate("list")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Agregar", color = TextColor, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { 
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonColor,
                            contentColor = TextColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }

    // Confirmación para eliminar lista
    if (showDeleteConfirmDialog && selectedList != null) {
        Dialog(onDismissRequest = { showDeleteConfirmDialog = false }) {
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
                        text = "¿Estás seguro de eliminar esta lista?",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                // Eliminar la lista
                                viewModel.deleteList(selectedList!!)
                                showDeleteConfirmDialog = false
                                showDialog = false
                                showDeleteSuccessDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6961)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Eliminar", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = { showDeleteConfirmDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar", color = TextColor, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Feedback de éxito al eliminar lista
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
                        text = "Lista eliminada con éxito",
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

    // Mostrar diálogo de instrucciones solo si no se está inspeccionando una lista
    if (showInstructions && !(showDialog && selectedList != null)) {
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
                        text = "- Selecciona la lista que deseas inspeccionar.\n\n - Usa el botón 'Eliminar' para borrar una lista que ya no necesites.\n\n- Usa el botón 'Agregar' para volver a comprar los productos de esa lista en la pantalla de compras.",
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
} 