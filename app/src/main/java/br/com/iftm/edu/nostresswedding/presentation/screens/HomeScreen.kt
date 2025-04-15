package br.com.iftm.edu.nostresswedding.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Aqui você pode adicionar os componentes da tela inicial
        // Por exemplo, um texto de boas-vindas ou uma lista de tarefas
        // Você pode usar o uid para buscar informações do usuário no banco de dados local
        // e exibir na tela inicial.

        // Exemplo de uso do uid para buscar informações do usuário
        val user = viewModel
        // Text(text = "Bem-vindo, ${user.name}!")
    }
}