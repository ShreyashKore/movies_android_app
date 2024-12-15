package dev.shreyash.androidmoviestask.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shreyash.androidmoviestask.ui.home.HomeScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = hiltViewModel(),
                onMovieClick = {
                    // Handle movie click
                }
            )
        }
    }
}