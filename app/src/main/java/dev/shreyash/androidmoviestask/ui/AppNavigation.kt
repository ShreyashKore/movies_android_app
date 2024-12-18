package dev.shreyash.androidmoviestask.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.shreyash.androidmoviestask.ui.home.HomeScreen
import dev.shreyash.androidmoviestask.ui.moviedetails.MovieDetailsScreen
import dev.shreyash.androidmoviestask.ui.moviedetails.MovieDetailsViewModel
import dev.shreyash.androidmoviestask.ui.videoplayer.VideoPlayer


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
                onMovieClick = { id ->
                    navController.navigate("movieDetails/$id")
                }
            )
        }

        composable("movieDetails/{movieId}") {
            MovieDetailsScreen(
                hiltViewModel<MovieDetailsViewModel>(),
                onTrailerClick = {
                    navController.navigate("videoPlayer/$it")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("videoPlayer/{videoUrl}") {
            VideoPlayer(
                videoKey = it.arguments?.getString("videoUrl") ?: ""
            )
        }
    }
}