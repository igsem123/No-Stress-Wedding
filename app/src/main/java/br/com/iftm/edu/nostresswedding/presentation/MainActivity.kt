package br.com.iftm.edu.nostresswedding.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.iftm.edu.nostresswedding.NoStressWeddingApp
import br.com.iftm.edu.nostresswedding.presentation.screens.LoginScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.RegisterScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel
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
                        NoStressWeddingApp(modifier = Modifier.fillMaxSize())
                        //RegisterScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun NoStressWeddingApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController() // Controlador de navegação

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Define suas rotas aqui
        composable("login") {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                modifier = modifier,
                loginViewModel = loginViewModel,
                onLoginClick = { /* Navegar para a próxima tela */ },
                onRegisterClick = { navController.navigate("register") }
            )
        }
        composable("register") { RegisterScreen() }
        // composable("home") { HomeScreen() }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    NoStressWeddingTheme {
        NoStressWeddingApp()
    }
}