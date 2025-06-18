package br.com.iftm.edu.nostresswedding.presentation.states

import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity

data class HomeUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val taskCreationState: TaskCreationState = TaskCreationState.Idle,
    val user: UserEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TaskCreationState {
    object Idle : TaskCreationState()
    object Loading : TaskCreationState()
    object Success : TaskCreationState()
    data class Error(val message: String) : TaskCreationState()
}