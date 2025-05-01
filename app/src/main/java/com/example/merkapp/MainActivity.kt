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
import com.example.merkapp.ui.screens.UserViewModel
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MerkAppTheme {
                val navController = rememberNavController()
                val userViewModel = remember { UserViewModel() } // instancia única compartida
                AppNavigation(navController, userViewModel)
            }
        }
    }
}


@Composable
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
        }
        composable("main") {
            MainScreen(navController)
        }
        composable("list") {
            ListScreen(navController)
        }
    }
}
