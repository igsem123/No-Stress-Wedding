package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations

internal fun NavGraphBuilder.vendorScreenNavigation(navController: NavController) {
    composable(NoStressWeddingDestinations.VendorScreen.route) {
        // VendorScreen()
    }
}

fun NavController.navigateToVendorScreen() {
    this.navigate(NoStressWeddingDestinations.VendorScreen.route)
}