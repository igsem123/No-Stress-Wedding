package br.com.iftm.edu.nostresswedding.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Edit
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.DollarSignSolid
import compose.icons.lineawesomeicons.Registered
import compose.icons.lineawesomeicons.User
import compose.icons.lineawesomeicons.UserLockSolid

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val state by viewModel.uiState
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    if (state.success) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                "Usuário cadastrado com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

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
        val dataPickerState = rememberDatePickerState()

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
            icon = LineAwesomeIcons.User,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            visualTransformation = VisualTransformation.None,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        CustomOutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onFieldChange("username", it) },
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            icon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            visualTransformation = VisualTransformation.None,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        CustomOutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onFieldChange("password", it) },
            label = "Senha",
            placeholder = "Digite sua senha",
            icon = Icons.Outlined.Lock,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        CustomOutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { viewModel.onFieldChange("confirmPassword", it) },
            label = "Confirmar Senha",
            placeholder = "Confirme sua senha",
            icon = LineAwesomeIcons.UserLockSolid,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (viewModel.isPasswordEqual()) {
                        focusManager.clearFocus()
                    }
                }
            ),
            supportingText = {
                if (!viewModel.isPasswordEqual() && state.confirmPassword.isNotEmpty()) {
                    Text(
                        text = "As senhas não coincidem!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
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
            icon = LineAwesomeIcons.DollarSignSolid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            visualTransformation = VisualTransformation.None,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
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
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400
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

        Spacer(Modifier.height(16.dp))
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
                repository = TODO()
            ), // Mock RegisterViewModel for preview
            navController = NavController(context = LocalContext.current) // Mock NavController for preview
        )
    }
}