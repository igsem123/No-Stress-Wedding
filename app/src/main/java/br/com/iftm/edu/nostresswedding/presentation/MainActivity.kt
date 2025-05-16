@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.iftm.edu.nostresswedding.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.iftm.edu.nostresswedding.NoStressWeddingApp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.presentation.screens.HomeScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.LoginScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.RegisterScreen
import br.com.iftm.edu.nostresswedding.presentation.screens.TopAppBarExpandable
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val userViewModel: HomeViewModel = hiltViewModel()
            userViewModel.getUserDataFromRoom(FirebaseAuth.getInstance().uid.toString())
            val user by userViewModel.user.collectAsState()
            NoStressWeddingApp(
                modifier = Modifier,
                userViewModel = userViewModel,
                user = user
            )
        }
    }
}

@Composable
fun NoStressWeddingApp(
    modifier: Modifier = Modifier,
    userViewModel: HomeViewModel,
    user: UserEntity? = null
) {
    val navController = rememberNavController() // Controlador de navegação
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isShowTopBarWithBackAction = currentRoute == "register"
    val isShowTopBarExpandable = when (currentRoute) {
        "home/{uid}" -> true
        "login" -> false
        else -> false
    }
    val logout = {
        userViewModel.logout()
        navController.navigate("login") {
            popUpTo("home/${user?.uid}") { inclusive = true }
        }
    }

    NoStressWeddingTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                if (isShowTopBarWithBackAction) {
                    TopAppBar(
                        title = {
                            Icon(
                                painter = painterResource(R.drawable.ic_login),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(40.dp)
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


                if (isShowTopBarExpandable) {
                    TopAppBarExpandable(user = user, logout = logout)
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
                            userViewModel.getTasksByUserId(uid)
                            val remainingDaysPhrase =
                                userViewModel.getCountTillWeddingDayInString(
                                    user?.weddingDate ?: ""
                                )
                            HomeScreen(
                                modifier = modifier,
                                user = user,
                                remainingDaysPhrase = remainingDaysPhrase,
                                viewmodel = userViewModel
                            )
                        }
                    }
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
