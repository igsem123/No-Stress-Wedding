package br.com.iftm.edu.nostresswedding.presentation.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations

internal fun NavGraphBuilder.paymentScreenNavigation() {
    // Implement the navigation for the Payment Screen here
    composable(NoStressWeddingDestinations.PaymentScreen.route) {
        // PaymentScreen()
     }
}

fun NavController.navigateToPaymentScreen() {
    // Implement the navigation action to the Payment Screen here
    this.navigate(NoStressWeddingDestinations.PaymentScreen.route)
}