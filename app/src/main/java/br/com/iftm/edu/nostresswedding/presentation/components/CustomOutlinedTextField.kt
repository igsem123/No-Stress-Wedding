package br.com.iftm.edu.nostresswedding.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.ui.theme.Pink40

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions?,
    visualTransformation: VisualTransformation?
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label,
                color = Pink40
            )
        },
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
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
        },
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        singleLine = true
    )
}