package br.com.iftm.edu.nostresswedding.presentation.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.presentation.viewmodels.RegisterViewModel
import br.com.iftm.edu.nostresswedding.ui.theme.Pink40

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange },
        label = {
            Text(
                text = label,
                color = Pink40
            )
        },
        modifier = modifier
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
                text = placeholder,
                color = Pink40,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Pink40,
                modifier = Modifier
                    .size(22.dp)
            )
        }
    )
}