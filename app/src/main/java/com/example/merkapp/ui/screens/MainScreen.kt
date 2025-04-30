package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, userName: String = "Cliente") {
    val sections = listOf(
        "Proteínas" to listOf("Carne", "Pollo", "Pescado", "Huevos"),
        "Lácteos" to listOf("Leche", "Queso", "Yogurt"),
        "Víveres" to listOf("Arroz", "Frijoles", "Aceite", "Sal"),
        "Frutas y Verduras" to listOf("Manzana", "Plátano", "Tomate", "Lechuga"),
        "Aseo" to listOf("Jabón", "Shampoo", "Papel Higiénico")
    )

    var selectedSection by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val productSelections = remember {
        mutableStateMapOf<String, Pair<Boolean, String>>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Bienvenido,", style = MaterialTheme.typography.titleLarge)
        Text(userName, style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de secciones
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedSection ?: "Seleccione una sección",
                onValueChange = {},
                readOnly = true,
                label = { Text("Sección") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                sections.forEach { (section, _) ->
                    DropdownMenuItem(
                        text = { Text(section) },
                        onClick = {
                            selectedSection = section
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar productos de la sección seleccionada
        selectedSection?.let { section ->
            Text("Productos de $section", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val products = sections.first { it.first == section }.second

            products.forEach { product ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    val selection = productSelections[product] ?: (false to "")
                    var isChecked by remember { mutableStateOf(selection.first) }
                    var quantity by remember { mutableStateOf(selection.second) }

                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            productSelections[product] = isChecked to quantity
                        }
                    )

                    Text(
                        text = product,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )

                    TextField(
                        value = quantity,
                        onValueChange = {
                            quantity = it
                            productSelections[product] = isChecked to quantity
                        },
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(100.dp)
                    )
                }
            }
        }
    }
}
