package com.example.merkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.merkapp.ui.screens.*
import com.example.merkapp.ui.theme.MerkAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
                var showSplash by remember { mutableStateOf(true) }
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(2000L)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen(navController)
                } else {
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("forgotPassword") {
            ForgotPasswordScreen(navController)
        }
        composable("main") {
            MainScreen(navController)
        }
    }
}