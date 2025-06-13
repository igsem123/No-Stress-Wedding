package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.presentation.screens.GuestScreen

internal fun NavGraphBuilder.guestScreenNavigation() {
    composable(NoStressWeddingDestinations.GuestScreen.route) {
        //val homeViewModel: HomeViewModel = hiltViewModel()
        GuestScreen()
    }
}

fun NavController.navigateToGuestScreen() {
    this.navigate(NoStressWeddingDestinations.GuestScreen.route) {
        popUpTo(NoStressWeddingDestinations.GuestScreen.route) { inclusive = true }
    }
}