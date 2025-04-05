package br.com.iftm.edu.nostresswedding.presentation.screens

import android.R.style
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.presentation.components.CustomOutlinedTextField
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
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

        Text(
            text = "Cadastro",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Preencha os campos com seus dados:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        CustomOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Nome",
            placeholder = "Digite seu nome",
            icon = Icons.Outlined.Person
        )
        CustomOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            icon = Icons.Outlined.Email
        )
        CustomOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Senha",
            placeholder = "Digite sua senha",
            icon = Icons.Outlined.Lock
        )
        CustomOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "Confirmar Senha",
            placeholder = "Confirme sua senha",
            icon = Icons.Outlined.Lock
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
                    style = MaterialTheme.typography.titleLarge,
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
                    style = MaterialTheme.typography.titleMedium,
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}