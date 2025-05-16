package br.com.iftm.edu.nostresswedding.presentation.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.presentation.components.TaskCard
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.HomeViewModel
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.TaskInsertState
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ClockSolid
import compose.icons.lineawesomeicons.PlusSolid

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: UserEntity?,
    remainingDaysPhrase: String,
    viewmodel: HomeViewModel,
) {
    var showFormDialog by remember { mutableStateOf(false) }
    val tasks by viewmodel.tasks.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Bem-vindo, ${user?.name}!\n Aproveite seu planner de casamento!",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 3.dp,
                        color = Color.Black.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = LineAwesomeIcons.ClockSolid,
                    contentDescription = "Contador",
                    modifier = Modifier
                        .fillMaxSize(0.4f),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = remainingDaysPhrase,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Próximas Tarefas:",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    onClick = { showFormDialog = true },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = LineAwesomeIcons.PlusSolid,
                        contentDescription = "Adicionar Tarefa",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }

        items(tasks) {
            TaskCard(
                task = it,
                onCompleteTask = {
                    viewmodel.onCompleteTask(it)
                }
            )
        }
    }

    if (showFormDialog) {
        Dialog(
            onDismissRequest = {
                showFormDialog = false
                viewmodel.clearTaskInsertState()
            }
        ) {
            FormBoxTask(
                viewmodel = viewmodel,
                user = user,
                showFormDialog = { showFormDialog = false }
            )
        }
    }
}

@Composable
fun FormBoxTask(viewmodel: HomeViewModel, user: UserEntity? = null, showFormDialog: () -> Unit) {
    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    val taskInsertState by viewmodel.taskInsertState.collectAsState()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Adicionar Tarefa",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text(text = "Título da Tarefa") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFFA6E81),
                unfocusedIndicatorColor = Color(0xFFFA6E81),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.large,
            placeholder = {
                Text(
                    text = "Digite o nome da tarefa",
                    color = Color(0xFFFA6E81),
                )
            },
            isError = taskInsertState is TaskInsertState.Error,
            supportingText = {
                if (taskInsertState is TaskInsertState.Error) {
                    Text(
                        text = (taskInsertState as TaskInsertState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text(text = "Descrição da Tarefa") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFFA6E81),
                unfocusedIndicatorColor = Color(0xFFFA6E81),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.large,
            placeholder = {
                Text(
                    text = "Digite a descrição da tarefa",
                    color = Color(0xFFFA6E81),
                )
            },
            maxLines = 5,
            isError = taskInsertState is TaskInsertState.Error,
            supportingText = {
                if (taskInsertState is TaskInsertState.Error) {
                    Text(
                        text = (taskInsertState as TaskInsertState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            },
        )

        Button(
            onClick = {
                viewmodel.insertTask(
                    task = TaskEntity(
                        title = taskTitle,
                        description = taskDescription,
                        userId = user?.uid ?: "",
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.5f),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFA6E81),
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            when (taskInsertState) {
                is TaskInsertState.Loading -> { CircularProgressIndicator() }
                is TaskInsertState.Success -> { showFormDialog() }
                else -> {
                    Text(
                        text = "Adicionar Tarefa",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBarExpandable(
    user: UserEntity?,
    logout: () -> Unit = {}
) {
    val maxHeight = 300.dp
    val minHeight = 48.dp
    var isExpanded by remember { mutableStateOf(false) }

    val appBarHeight by animateDpAsState(
        targetValue = if (isExpanded) maxHeight else minHeight,
        label = "AppBarHeightAnimation"
    )

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "ArrowRotation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(appBarHeight)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { isExpanded = !isExpanded },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isExpanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Informações do Usuário",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nome: ${user?.name ?: "..."}", color = Color.White)
                    Text("Email: ${user?.email ?: "..."}", color = Color.White)
                    Text("Casamento: ${user?.weddingDate ?: "..."}", color = Color.White)
                    Text("Orçamento: R$ ${user?.weddingBudget ?: "..."}", color = Color.White)
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_login),
                    contentDescription = "Logo"
                )
            }

            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Recolher" else "Expandir",
                modifier = Modifier
                    .rotate(rotationAngle),
                tint = Color.White
            )
        }
    }
}
