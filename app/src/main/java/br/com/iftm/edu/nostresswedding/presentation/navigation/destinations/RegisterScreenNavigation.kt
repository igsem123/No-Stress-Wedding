package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.presentation.screens.RegisterScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel

internal fun NavGraphBuilder.registerScreenNavigation(navController: NavController) {
    composable(NoStressWeddingDestinations.RegisterScreen.route) {
        val viewModel: RegisterViewModel = hiltViewModel()
        RegisterScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}

fun NavController.navigateToRegisterScreen() {
    this.navigate(NoStressWeddingDestinations.RegisterScreen.route)
}