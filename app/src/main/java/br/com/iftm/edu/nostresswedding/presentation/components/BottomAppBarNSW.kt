package br.com.iftm.edu.nostresswedding.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomAppBarNSW(
    scrollBehavior: BottomAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    bottomItems: List<Triple<String, ImageVector, () -> Unit>>
) {
    var selectedIndex by remember { mutableIntStateOf(2) }

    FlexibleBottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = BottomAppBarDefaults.FlexibleHorizontalArrangement,
        scrollBehavior = scrollBehavior,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        bottomItems.forEachIndexed { index, item ->

            val selectedItemColor by animateColorAsState(
                targetValue = if (selectedIndex == index ) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = spring()
            )

            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 1.dp)
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val indicatorColor by animateColorAsState(
                    targetValue = if (selectedIndex == index) MaterialTheme.colorScheme.primary
                    else Color.Transparent,
                    label = "IndicatorColor"
                )

                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(4.dp)
                        .fillMaxWidth()
                        .background(color = indicatorColor),
                    content = {}
                )

                IconButton(
                    onClick = {
                        selectedIndex = index
                        item.third.invoke()
                    },
                    modifier = modifier
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        tint = selectedItemColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = item.first,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    textAlign = TextAlign.Center,
                    color = selectedItemColor
                )
            }
        }
    }
}