package com.example.merkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.merkapp.ui.screens.*
import com.example.merkapp.ui.theme.MerkAppTheme
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()
    val shoppingListViewModel: ShoppingListViewModel = viewModel()
    val userState by userViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("main") {
            LaunchedEffect(userState.isLoggedIn) {
                if (!userState.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            MainScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("list") {
            LaunchedEffect(userState.isLoggedIn) {
                if (!userState.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            ListScreen(navController = navController, shoppingListViewModel = shoppingListViewModel)
        }
        composable("mylists") {
            LaunchedEffect(userState.isLoggedIn) {
                if (!userState.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            MyListsScreen(navController = navController, viewModel = shoppingListViewModel)
        }
        composable("config") {
            LaunchedEffect(userState.isLoggedIn) {
                if (!userState.isLoggedIn) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            ConfigScreen(navController = navController)
        }
    }
}
