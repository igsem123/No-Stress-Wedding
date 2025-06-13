package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations

internal fun NavGraphBuilder.giftScreenNavigation(navController: NavController) {
    // Implement the navigation for the Gift Screen here
    composable(NoStressWeddingDestinations.GiftScreen.route) {
    //     GiftScreen()
    }
}

fun NavController.navigateToGiftScreen() {
    // Implement the navigation action to the Gift Screen here
    this.navigate(NoStressWeddingDestinations.GiftScreen.route)
}