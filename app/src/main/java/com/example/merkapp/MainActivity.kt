package com.example.merkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.merkapp.ui.screens.*
import com.example.merkapp.ui.theme.MerkAppTheme
import com.example.merkapp.ui.viewmodels.UserViewModel
import com.example.merkapp.ui.viewmodels.ShoppingListViewModel
import com.example.merkapp.ui.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private val shoppingListViewModel: ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userState by userViewModel.uiState.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") {
                            SplashScreen(navController, themeViewModel)
                        }
                        composable("login") {
                            LoginScreen(navController, userViewModel, themeViewModel)
                        }
                        composable("register") {
                            RegisterScreen(navController, userViewModel, themeViewModel)
                        }
                        composable("forgot_password") {
                            ForgotPasswordScreen(navController)
                        }
                        composable("contact") {
                            ContactScreen(navController, themeViewModel)
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
                                    userViewModel = userViewModel,
                                    themeViewModel = themeViewModel
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
                                    navController = navController,
                                    userViewModel = userViewModel,
                                    themeViewModel = themeViewModel
                                )
                            }
                        }
                        composable("my_lists") {
                            if (!userState.isLoggedIn) {
                                LaunchedEffect(Unit) {
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            } else {
                                MyListsScreen(
                                    navController = navController,
                                    viewModel = shoppingListViewModel,
                                    userViewModel = userViewModel,
                                    themeViewModel = themeViewModel
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
                                    shoppingListViewModel = shoppingListViewModel,
                                    themeViewModel = themeViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
