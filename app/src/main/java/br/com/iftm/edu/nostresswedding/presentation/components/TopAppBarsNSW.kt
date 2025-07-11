package br.com.iftm.edu.nostresswedding.presentation.components

import android.graphics.fonts.Font
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.R
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowDownSolid

@Composable
fun TopAppBarExpandable(
    user: UserEntity?,
    logout: () -> Unit = {}
) {
    val maxHeight = 320.dp
    val minHeight = 116.dp
    var isExpanded by remember { mutableStateOf(false) }

    val pulseAnimation = animateFloatAsState(
        targetValue = if (isExpanded) 1.4f else 1f,
        animationSpec = infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(durationMillis = 1000),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "PulseAnimation"
    )

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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp)
        ) {
            if (isExpanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(0.4f))
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
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
                    TextButton(
                        onClick = logout,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Sair",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_login),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(52.dp),
                )
            }

            Icon(
                imageVector = LineAwesomeIcons.ArrowDownSolid,
                contentDescription = if (isExpanded) "Recolher" else "Expandir",
                modifier = Modifier
                    .padding(4.dp)
                    .rotate(rotationAngle)
                    .size(20.dp * pulseAnimation.value),
                tint = Color.White
            )
        }
    }
}