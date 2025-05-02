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
<<<<<<< HEAD
import com.example.merkapp.ui.viewmodels.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
=======
import com.example.merkapp.ui.screens.UserViewModel
import androidx.compose.runtime.remember

>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
<<<<<<< HEAD
                Navigation()
=======
                val navController = rememberNavController()
                val userViewModel = remember { UserViewModel() } // instancia única compartida
                AppNavigation(navController, userViewModel)
>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
            }
        }
    }
}


@Composable
<<<<<<< HEAD
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
=======
fun AppNavigation(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController, userViewModel)
        }
        composable("register") {
            RegisterScreen(navController, userViewModel)
        }
        composable("forgotPassword") {
            ForgotPasswordScreen(navController)
>>>>>>> f2ff4ecc50fcac691c1628cca3cd394589439778
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
