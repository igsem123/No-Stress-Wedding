package br.com.iftm.edu.nostresswedding.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FlexibleBottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.CreditCardSolid
import compose.icons.lineawesomeicons.GiftSolid
import compose.icons.lineawesomeicons.HomeSolid
import compose.icons.lineawesomeicons.TruckSolid
import compose.icons.lineawesomeicons.UsersSolid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomAppBarNSW(scrollBehavior : BottomAppBarScrollBehavior, modifier: Modifier = Modifier) {
    val bottomItems = listOf<Triple<String, ImageVector, String>>(
        Triple("Convidados", LineAwesomeIcons.UsersSolid, "Guests"),
        Triple("Fornecedores", LineAwesomeIcons.TruckSolid, "Vendors"),
        Triple("Home", LineAwesomeIcons.HomeSolid, "Home"),
        Triple("Presentes", LineAwesomeIcons.GiftSolid, "Gifts"),
        Triple("Pagamentos", LineAwesomeIcons.CreditCardSolid, "Payments"),
    )

    FlexibleBottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = BottomAppBarDefaults.FlexibleHorizontalArrangement,
        scrollBehavior = scrollBehavior
    ) {
        bottomItems.forEach { item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { /* TODO: Handle navigation */ },
                    modifier = modifier
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = item.first,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                )
            }
        }
    }
}