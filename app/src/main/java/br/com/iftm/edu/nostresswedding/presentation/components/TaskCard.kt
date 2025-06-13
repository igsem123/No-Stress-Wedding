package br.com.iftm.edu.nostresswedding.presentation.components

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import com.google.android.gms.tasks.Task
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.CheckSolid
import compose.icons.lineawesomeicons.Edit
import compose.icons.lineawesomeicons.EditSolid
import compose.icons.lineawesomeicons.TrashSolid
import compose.icons.lineawesomeicons.WindowClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TaskCard(
    task: TaskEntity,
    onCompleteTask: () -> Unit = {},
    onDeleteTask: () -> Unit = {},
    onEditTask: (TaskEntity) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    if (!task.isCompleted) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize()
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            content = {
                TaskCardContent(
                    task = task,
                    onCompleteTask = onCompleteTask,
                    expanded = expanded
                )
            }
        )
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .animateContentSize()
                .clickable { expanded = !expanded },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
            ),
            content = {
                TaskCardContent(
                    task = task,
                    onCompleteTask = onCompleteTask,
                    expanded = expanded,
                    onDeleteTask = onDeleteTask,
                    onEditTask = onEditTask
                )
            }
        )
    }
}

@Composable
fun TaskCardContent(
    task: TaskEntity,
    onCompleteTask: () -> Unit,
    expanded: Boolean,
    onDeleteTask: () -> Unit = {},
    onEditTask: (TaskEntity) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(task.title) }
    var editedDescription by remember { mutableStateOf("Toque e segure por 3 segundos para editar!") }

    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(0.6f)
                .wrapContentWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            Toast.makeText(context, "Segure para editar!", Toast.LENGTH_SHORT).show()
                        },
                        onLongPress = {
                            isEditing = true
                        }
                    )
                }
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = editedTitle,
                    onValueChange = { editedTitle = it },
                    maxLines = 1,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isEditing = false
                                if (editedTitle.isNotBlank()) {
                                    val updatedTask = task.copy(title = editedTitle)
                                    onEditTask(updatedTask)
                                } else {
                                    Toast.makeText(context, "Título não pode ser vazio!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = LineAwesomeIcons.CheckSolid,
                                contentDescription = "Salvar Tarefa",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                isEditing = false
                                editedTitle = task.title // Reset to original title
                            }
                        ) {
                            Icon(
                                imageVector = LineAwesomeIcons.WindowClose,
                                contentDescription = "Cancelar Edição",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
            } else {
                Text(
                    text = task.title,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                task.isCompleted = it
                onCompleteTask()
            },
            modifier = Modifier,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = Color.White
            )
        )
    }

    if (expanded) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = task.description,
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 4,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
                IconButton(
                    onClick = { onDeleteTask() }
                ) {
                    Icon(
                        imageVector = LineAwesomeIcons.TrashSolid,
                        contentDescription = "Delete Task",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}