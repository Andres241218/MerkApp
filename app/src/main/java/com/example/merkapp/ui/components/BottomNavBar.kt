package com.example.merkapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.merkapp.ui.viewmodels.ThemeViewModel
import androidx.compose.material.icons.filled.Info

private val BackgroundColor = Color(0xFFDEB887) // #DEB887
private val ButtonColor = Color(0xFFCE8540)     // #CE8540
private val TextColor = Color(0xFF314401)       // #314401
private val PanelColor = Color(0xFFF0E68C)       // #F0E68C

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("main", Icons.Default.Home, "Inicio")
    object Lists : BottomNavItem("my_lists", Icons.Default.List, "Mis Listas")
    object Config : BottomNavItem("config", Icons.Default.Settings, "Configuración")
    object Contact : BottomNavItem("contact", Icons.Default.Info, "Contacto")

}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val PanelColor = if (isDarkMode) Color(0xFF312C9B) else Color(0xFFF0E68C)
    val BackgroundColor = if (isDarkMode) PanelColor else Color(0xFFDEB887)
    val TextColor = if (isDarkMode) Color.White else Color(0xFF314401)

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Lists,
        BottomNavItem.Config,
        BottomNavItem.Contact,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        containerColor = BackgroundColor,
        modifier = modifier
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color.Black else TextColor
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) Color.Black else TextColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = if (selected) PanelColor else BackgroundColor
                )
            )
        }
    }
} 