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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ClockSolid
import compose.icons.lineawesomeicons.PlusSolid

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: UserEntity?,
    logout: () -> Unit = {},
    remainingDaysPhrase: String,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            TopAppBarExpandable(user = user, logout = logout)
        }
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
            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(width = 3.dp, color =  Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(20.dp))
            ) {
                Icon(
                    imageVector = LineAwesomeIcons.ClockSolid,
                    contentDescription = "Contador",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = remainingDaysPhrase,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
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
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = LineAwesomeIcons.PlusSolid,
                        contentDescription = "Adicionar Tarefa",
                        tint = MaterialTheme.colorScheme.onPrimary,
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
    val minHeight = 80.dp
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
