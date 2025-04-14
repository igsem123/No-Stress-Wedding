package br.com.iftm.edu.nostresswedding.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.LoginViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.Pink40

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier.height(1.dp)
        )
        Image(
            modifier = Modifier.padding(bottom = 16.dp),
            painter = painterResource(id = R.drawable.no_stress_wedding_2_),
            contentDescription = "Logo"
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val email by loginViewModel.email.collectAsState()
            val password by loginViewModel.password.collectAsState()

            OutlinedTextField(
                value = email,
                onValueChange = { loginViewModel.updateEmail(it) },
                label = { Text(text = "Usuário", color = Pink40) },
                modifier = Modifier.padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFA6E81),
                    unfocusedIndicatorColor = Color(0xFFFA6E81),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                shape = MaterialTheme.shapes.large,
                placeholder = {
                    Text(
                        text = "Digite seu usuário",
                        color = Pink40,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "User",
                        tint = Pink40,
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { loginViewModel.updatePassword(it) },
                label = { Text(text = "Senha", color = Pink40) },
                modifier = Modifier.padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFA6E81),
                    unfocusedIndicatorColor = Color(0xFFFA6E81),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                shape = MaterialTheme.shapes.large,
                placeholder = {
                    Text(
                        text = "Digite sua senha",
                        color = Pink40,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Password",
                        tint = Pink40,
                        modifier = Modifier
                            .size(22.dp)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
            )
            TextButton(
                onClick = { onLoginClick },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Pink40,
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Fingerprint",
                    tint = Pink40,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp)
                )
                Text(
                    text = "Entrar",
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
        TextButton(
            onClick = { onRegisterClick() },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .width(220.dp)
                .border(
                    width = 1.dp,
                    color = Pink40,
                    shape = MaterialTheme.shapes.extraLarge
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Pink40
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = "Não tem conta? Cadastre-se!",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        modifier = Modifier.fillMaxSize(),
        loginViewModel = LoginViewModel()
    )
}