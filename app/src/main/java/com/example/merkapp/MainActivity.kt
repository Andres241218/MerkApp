package com.example.merkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.merkapp.ui.screens.*
import com.example.merkapp.ui.theme.MerkAppTheme
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val shoppingListViewModel: ShoppingListViewModel = viewModel()
    val userState by userViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }
        composable("main") {
            if (!userState.isLoggedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                MainScreen(
                    navController = navController,
                    userViewModel = userViewModel
                )
            }
        }
        composable("list") {
            if (!userState.isLoggedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                ListScreen(
                    navController = navController,
                    shoppingListViewModel = shoppingListViewModel
                )
            }
        }
        composable("mylists") {
            if (!userState.isLoggedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                MyListsScreen(
                    navController = navController,
                    viewModel = shoppingListViewModel
                )
            }
        }
        composable("config") {
            if (!userState.isLoggedIn) {
                LaunchedEffect(Unit) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            } else {
                ConfigScreen(
                    navController = navController
                )
            }
        }
    }
}
