package com.example.s8144281assignment2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.s8144281assignment2.ui.DashboardScreen
import com.example.s8144281assignment2.ui.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(onLoginSuccess = { keypass ->
                navController.navigate("dashboard/$keypass")
            })
        }

        composable("dashboard/{keypass}") { backStackEntry ->
            val keypass = backStackEntry.arguments?.getString("keypass") ?: ""
            DashboardScreen(keypass)
        }
    }
}