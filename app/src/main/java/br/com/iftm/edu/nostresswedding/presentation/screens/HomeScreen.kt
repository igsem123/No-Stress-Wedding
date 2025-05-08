package br.com.iftm.edu.nostresswedding.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity

@Composable
fun HomeScreen(modifier: Modifier = Modifier, user: UserEntity?, logout: () -> Unit = {}) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = rememberLazyListState()
    ) {
        item {
            TopAppBarExpandable(user = user, logout = logout)
        }
        item {
            Text(text = "Bem-vindo, ${user?.name}!")
        }
    }
}

@Composable
fun TopAppBarExpandable(
    user: UserEntity?,
    logout: () -> Unit = {}
) {
    val maxHeight = 300.dp
    val minHeight = 56.dp
    var isExpanded by remember { mutableStateOf(false) }

    val appBarHeight by animateDpAsState(
        targetValue = if (isExpanded) maxHeight else minHeight,
        label = "AppBarHeightAnimation"
    )

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "ArrowRotation"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(appBarHeight)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_login),
                        contentDescription = "Logo"
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Recolher" else "Expandir",
                        modifier = Modifier.rotate(rotationAngle),
                        tint = Color.White
                    )
                }
            }
        }
    }
}
