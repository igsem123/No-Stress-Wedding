package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.presentation.screens.HomeScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel

internal fun NavGraphBuilder.homeScreenNavigation(homeViewModel: HomeViewModel) {
    composable(NoStressWeddingDestinations.HomeScreen.route) {
        HomeScreen(viewmodel = homeViewModel)
    }
}

fun NavController.navigateToHomeScreen() {
    this.navigate(NoStressWeddingDestinations.HomeScreen.route) {
        popUpTo(NoStressWeddingDestinations.LoginScreen.route) { inclusive = true }
    }
}