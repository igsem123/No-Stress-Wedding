@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.iftm.edu.nostresswedding.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.iftm.edu.nostresswedding.NoStressWeddingApp
import br.com.iftm.edu.nostresswedding.presentation.components.BottomAppBarNSW
import br.com.iftm.edu.nostresswedding.presentation.components.TopAppBarExpandable
import br.com.iftm.edu.nostresswedding.presentation.navigation.NoStressWeddingDestinations
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToGiftScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToGuestScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToHomeScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToLoginScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToPaymentScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.destinations.navigateToVendorScreen
import br.com.iftm.edu.nostresswedding.presentation.navigation.noStressWeddingGraph
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import com.google.firebase.auth.FirebaseAuth
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import compose.icons.lineawesomeicons.CreditCardSolid
import compose.icons.lineawesomeicons.GiftSolid
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.TruckSolid
import compose.icons.lineawesomeicons.UsersSolid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoStressWeddingTheme {
                NoStressWeddingApp(modifier = Modifier)
            }
        }
    }
}

@Composable
fun NoStressWeddingApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController() // Controlador de navegação
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isShowTopBarWithBackAction =
        currentRoute == NoStressWeddingDestinations.RegisterScreen.route

    val isShowTopBarExpandable = when (currentRoute) {
        NoStressWeddingDestinations.HomeScreen.route -> true
        NoStressWeddingDestinations.LoginScreen.route -> false
        else -> false
    }

    val viewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (!uid.isNullOrBlank()) {
            viewModel.getUserDataFromRoom(uid)
        }
    }

    val user by viewModel.user.collectAsState()
    val logout = {
        viewModel.logout()
        navController.navigateToLoginScreen()
    }

    // Comportamento de rolagem para BottomAppBar
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (isShowTopBarWithBackAction) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Voltar",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                contentDescription = "Ícone de voltar",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            } else if (isShowTopBarExpandable) {
                TopAppBarExpandable(user = user, logout = logout)
            }
        },
        bottomBar = {
            when (currentRoute) {
                NoStressWeddingDestinations.LoginScreen.route,
                NoStressWeddingDestinations.RegisterScreen.route -> return@Scaffold // Não exibe BottomAppBar nas telas de login e registro
            }

            val bottomItems = listOf<Triple<String, ImageVector, () -> Unit>>(
                Triple("Convidados", LineAwesomeIcons.UsersSolid) { navController.navigateToGuestScreen() },
                Triple("Fornecedores", LineAwesomeIcons.TruckSolid) { navController.navigateToVendorScreen() },
                Triple("Home", LineAwesomeIcons.HomeSolid) { navController.navigateToHomeScreen() },
                Triple("Presentes", LineAwesomeIcons.GiftSolid) { navController.navigateToGiftScreen() },
                Triple("Pagamentos", LineAwesomeIcons.CreditCardSolid) { navController.navigateToPaymentScreen() },
            )

            BottomAppBarNSW(scrollBehavior = scrollBehavior, bottomItems = bottomItems)
        }
    ) { innerPadding ->
        val listState = rememberLazyListState()
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = NoStressWeddingDestinations.LoginScreen.route,
            ) {
                noStressWeddingGraph(
                    navController = navController,
                    listState = listState,
                    homeViewModel = viewModel
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NoStressWeddingTheme {
        NoStressWeddingApp()
    }
}
