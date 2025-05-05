package com.example.merkapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen() {
    val backgroundColor = Color(0xFFDEB887)

    Surface(color = backgroundColor, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Contacto") })
            },
            containerColor = Color.Transparent // Para que Scaffold no sobrescriba el fondo
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Víctor Martínez",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("📞 +57 3008388097")
                Text("✉️ victor.martinezt@upb.edu.co")
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Andrés Sáenz",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("📞 +3125792542")
                Text("✉️ andres.saenz@upb.edu.co")
            }
        }
    }
}
