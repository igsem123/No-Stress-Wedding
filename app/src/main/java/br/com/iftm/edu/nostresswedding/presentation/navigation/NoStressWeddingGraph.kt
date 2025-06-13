package br.com.iftm.edu.nostresswedding.presentation.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.giftScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.guestScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.homeScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.loginScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.paymentScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.registerScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.vendorScreenNavigation
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel

fun NavGraphBuilder.noStressWeddingGraph(
    navController: NavController,
    listState: LazyListState,
    homeViewModel: HomeViewModel
) {
    loginScreenNavigation(navController)
    registerScreenNavigation(navController)
    homeScreenNavigation(homeViewModel)
    guestScreenNavigation()
    vendorScreenNavigation(navController)
    giftScreenNavigation(navController)
    paymentScreenNavigation()
}