@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.iftm.edu.nostresswedding.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.iftm.edu.nostresswedding.NoStressWeddingApp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.presentation.screens.HomeScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.LoginScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.RegisterScreen
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.currentBackStackEntryAsState

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
                        NoStressWeddingApp(modifier = Modifier)
                    }
                }
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

    val isShowTopBarWithBackAction = currentRoute == "register"

    Column {
        if (isShowTopBarWithBackAction) {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.ic_login),
                        contentDescription = "Logo",
                        Modifier.size(56.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Ícone de voltar"
                        )
                    }
                }
            )
        }


        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                // Define suas rotas aqui
                composable("login") {
                    val viewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(
                        modifier = modifier,
                        loginViewModel = viewModel,
                        onLoginClick = { viewModel.login() },
                        onRegisterClick = { navController.navigate("register") },
                        onLoginSuccess = { uid ->
                            navController.navigate("home/$uid") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }
                composable("register") {
                    val viewModel: RegisterViewModel = hiltViewModel()
                    RegisterScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                composable(route = "home/{uid}") {
                    val uid = it.arguments?.getString("uid") ?: ""
                    val viewmodel: HomeViewModel = hiltViewModel()

                    viewmodel.getUserDataFromRoom(uid)
                    val user by viewmodel.user.collectAsState()
                    HomeScreen(
                        modifier = modifier,
                        user = user,
                        logout = {
                            viewmodel.logout()
                            navController.navigate("login") {
                                popUpTo("home/$uid") { inclusive = true }
                            }
                        },
                    )
                }
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
