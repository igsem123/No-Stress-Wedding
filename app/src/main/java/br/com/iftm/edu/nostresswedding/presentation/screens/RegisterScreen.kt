package br.com.iftm.edu.nostresswedding.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.iftm.edu.nostresswedding.presentation.components.CustomOutlinedTextField
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import java.time.LocalDateTime
import java.time.ZoneOffset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val state by viewModel.uiState

    if (state.success) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo("register") {
                    inclusive = true
                }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val dataPickerState = rememberDatePickerState(
            initialSelectedDateMillis = LocalDateTime.now()
                .atZone(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli(),
        )

        viewModel.onFieldChange(
            "weddingDate", dataPickerState.selectedDateMillis?.let {
                LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC)
            }.toString()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                Text(
                    text = "Cadastre-se",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Preencha os campos com seus dados:",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier,
                    textAlign = TextAlign.Start
                )
            }
        }
        CustomOutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onFieldChange("name", it) },
            label = "Nome",
            placeholder = "Digite seu nome",
            icon = Icons.Outlined.Person,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = VisualTransformation.None
        )
        CustomOutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onFieldChange("username", it) },
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            icon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            visualTransformation = VisualTransformation.None
        )
        CustomOutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onFieldChange("password", it) },
            label = "Senha",
            placeholder = "Digite sua senha",
            icon = Icons.Outlined.Lock,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        CustomOutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.onFieldChange("confirmPassword", it) },
            label = "Confirmar Senha",
            placeholder = "Confirme sua senha",
            icon = Icons.Outlined.Create,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        DatePicker(
            state = dataPickerState,
            dateFormatter = remember {
                DatePickerDefaults.dateFormatter(
                    yearSelectionSkeleton = "YYYY",
                    selectedDateSkeleton = "mm",
                    selectedDateDescriptionSkeleton = "dd"
                )
            },
            modifier = Modifier.padding(horizontal = 24.dp),
            title = {
                Text(
                    text = "Qual a data do seu casamento?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            },
            headline = {
                Text(
                    text = "Alterar modo de exibição:",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                weekdayContentColor = MaterialTheme.colorScheme.primary,
                subheadContentColor = MaterialTheme.colorScheme.primary
            )
        )
        CustomOutlinedTextField(
            value = state.weddingBudget.toString(),
            onValueChange = { viewModel.onFieldChange("weddingBudget", it) },
            label = "Orçamento do Casamento",
            placeholder = "Digite o valor do orçamento",
            icon = Icons.Outlined.Lock,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None
        )

        Text(
            text = "Ao clicar em cadastrar, você concorda com nossos termos de uso e política de privacidade.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        } else {
            TextButton(
                onClick = { viewModel.registerUser() },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            ) {
                Text(
                    text = "Cadastrar",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light
                )
            }
        }

        state.error?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    NoStressWeddingTheme {
        RegisterScreen(
            modifier = Modifier,
            viewModel = RegisterViewModel(
                db = TODO()
            ), // Mock RegisterViewModel for preview
            navController = NavController(context = LocalContext.current) // Mock NavController for preview
        )
    }
}