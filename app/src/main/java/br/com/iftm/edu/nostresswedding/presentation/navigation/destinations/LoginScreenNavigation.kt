package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.presentation.screens.LoginScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel

internal fun NavGraphBuilder.loginScreenNavigation(
    navController: NavController,
) {
    composable(NoStressWeddingDestinations.LoginScreen.route) {
        val viewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            modifier = Modifier,
            loginViewModel = viewModel,
            onLoginClick = { viewModel.login() },
            onRegisterClick = { navController.navigateToRegisterScreen() },
            onLoginSuccess = { navController.navigateToHomeScreen() }
        )
    }
}

fun NavController.navigateToLoginScreen() {
    this.navigate(NoStressWeddingDestinations.LoginScreen.route) {
        popUpTo(NoStressWeddingDestinations.HomeScreen.route) { inclusive = true }
    }
}