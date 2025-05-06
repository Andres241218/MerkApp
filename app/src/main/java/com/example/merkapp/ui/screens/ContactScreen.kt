package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.merkapp.R
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import com.example.merkapp.ui.components.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val BackgroundColor = remember(isDarkMode) { if (isDarkMode) Color(0xFF252440) else Color(0xFFDEB887) }
    val TextColor = remember(isDarkMode) { if (isDarkMode) Color.White else Color(0xFF314401) }
    val logoRes = remember(isDarkMode) { if (isDarkMode) R.drawable.icw_logo else R.drawable.logo }

    Surface(color = BackgroundColor, modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = logoRes),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Contacto",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDarkMode) Color(0xFF312C9B) else Color(0xFFD4A76A)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Víctor Martínez",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text("📞 +57 3008388097", color = TextColor)
                        Text("✉️ victor.martinezt@upb.edu.co", color = TextColor)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDarkMode) Color(0xFF312C9B) else Color(0xFFD4A76A)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Andrés Sáenz",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text("📞 +57 3125792542", color = TextColor)
                        Text("✉️ andres.saenz@upb.edu.co", color = TextColor)
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
}
