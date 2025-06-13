package br.com.iftm.edu.nostresswedding.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.iftm.edu.nostresswedding.presentation.components.CustomOutlinedTextField
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.UserRegistrationState
import br.com.iftm.edu.nostresswedding.ui.theme.NoStressWeddingTheme
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.DollarSignSolid
import compose.icons.lineawesomeicons.Envelope
import compose.icons.lineawesomeicons.LockSolid
import compose.icons.lineawesomeicons.User
import compose.icons.lineawesomeicons.UserLockSolid
import kotlinx.coroutines.flow.filterNotNull
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    navController: NavController
) {
    val formState by viewModel.formState
    val uiState = viewModel.userRegistrationState
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(uiState.value) {
        if (uiState.value is UserRegistrationState.Error) {
            val errorMessage = (uiState.value as UserRegistrationState.Error).message
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        } else if (uiState.value is UserRegistrationState.Success) {
            Toast.makeText(
                context,
                "Usuário cadastrado com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearForm()
            viewModel.resetRegistrationState()

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

        LaunchedEffect(dataPickerState) {
            snapshotFlow { dataPickerState.selectedDateMillis }
                .filterNotNull()
                .collect { millis ->
                    val date = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .toString()
                    viewModel.onFieldChange("weddingDate", date)
                }
        }


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
            value = formState.name,
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
            value = formState.username,
            onValueChange = { viewModel.onFieldChange("username", it) },
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            icon = LineAwesomeIcons.Envelope,
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
            value = formState.password,
            onValueChange = { viewModel.onFieldChange("password", it) },
            label = "Senha",
            placeholder = "Digite sua senha",
            icon = LineAwesomeIcons.LockSolid,
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
            value = formState.confirmPassword,
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
                if (!viewModel.isPasswordEqual() && formState.confirmPassword.isNotEmpty()) {
                    Text(
                        text = "As senhas não coincidem!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        val dateFormatter = remember {
            DatePickerDefaults.dateFormatter(
                yearSelectionSkeleton = "yMMMM",
                selectedDateSkeleton = "yMMMd",
                selectedDateDescriptionSkeleton = "EEEE, d 'de' MMMM 'de' y" // "quarta-feira, 7 de maio de 2025"
            )
        }

        DatePicker(
            state = dataPickerState,
            dateFormatter = dateFormatter,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(24.dp)),
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
                Column {
                    Text(
                        text = "Alterar modo de exibição:",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                    DatePickerDefaults.DatePickerHeadline(
                        selectedDateMillis = dataPickerState.selectedDateMillis,
                        displayMode = DisplayMode.Input,
                        dateFormatter = dateFormatter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                weekdayContentColor = MaterialTheme.colorScheme.primary,
                subheadContentColor = MaterialTheme.colorScheme.primary
            )
        )

        CustomOutlinedTextField(
            value = formState.weddingBudget.toString(),
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

        if (uiState.value is UserRegistrationState.Loading) {
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

        if (uiState.value is UserRegistrationState.Error) {
            Text(
                text = (uiState.value as UserRegistrationState.Error).message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.error, CircleShape)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

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