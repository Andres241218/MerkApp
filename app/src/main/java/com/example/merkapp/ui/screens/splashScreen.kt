package com.example.merkapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import com.example.merkapp.R
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState

@Composable
fun SplashScreen(navController: NavHostController, themeViewModel: ThemeViewModel) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val backgroundColor = if (isDarkMode) Color(0xFF252440) else Color(0xFFDEB887)
    val logoRes = if (isDarkMode) R.drawable.icw_logo else R.drawable.logo
    LaunchedEffect(Unit) {
        delay(2000L) // Espera 2 segundos
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true } // Elimina "splash" del stack
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = "Logo de la aplicación"
        )
    }
}
