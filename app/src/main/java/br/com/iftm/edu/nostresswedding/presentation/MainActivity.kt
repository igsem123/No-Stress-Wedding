package br.com.iftm.edu.nostresswedding.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.iftm.edu.nostresswedding.NoStressWeddingApp
import br.com.iftm.edu.nostresswedding.presentation.screens.HomeScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.LoginScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.RegisterScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoStressWeddingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val registerViewModel = hiltViewModel<RegisterViewModel>()
                        val loginViewModel = hiltViewModel<LoginViewModel>()
                        val homeViewModel = hiltViewModel<HomeViewModel>()
                        NoStressWeddingApp(
                            modifier = Modifier.fillMaxSize(),
                            registerViewModel = registerViewModel,
                            loginViewModel = loginViewModel,
                            homeViewModel = homeViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoStressWeddingApp(
    modifier: Modifier = Modifier,
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
) {
    val navController = rememberNavController() // Controlador de navegação

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Define suas rotas aqui
        composable("login") {
            LoginScreen(
                modifier = modifier,
                loginViewModel = loginViewModel,
                onLoginClick = { loginViewModel.login() },
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = { uid ->
                    navController.navigate("home/$uid") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                navController = navController
            )
        }
        composable(route = "home/{uid}") {
            HomeScreen(
                modifier = modifier,
                viewModel = homeViewModel
            )
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