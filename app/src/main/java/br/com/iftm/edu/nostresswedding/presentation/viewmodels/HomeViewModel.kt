package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.core.utils.toLongDate
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.data.repository.LoginRepository
import br.com.iftm.edu.nostresswedding.data.repository.TaskRepository
import br.com.iftm.edu.nostresswedding.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private var _user: MutableStateFlow<UserEntity?> = MutableStateFlow(null)
    val user: MutableStateFlow<UserEntity?>
        get() = _user

    private val _tasks: MutableStateFlow<List<TaskEntity>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<TaskEntity>>
        get() = _tasks

    private val _taskInsertState: MutableStateFlow<TaskInsertState> = MutableStateFlow(TaskInsertState.Idle)
    val taskInsertState: StateFlow<TaskInsertState>
        get() = _taskInsertState

    fun getUserDataFromRoom(
        uid: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = userRepository.getUserFromRoom(uid)
        }
    }

    fun logout() {
        loginRepository.auth.signOut()
    }

    fun getCountTillWeddingDay(weddingDate: String): Int {
        // Data no padrão 2034-07-10
        val currentDate = System.currentTimeMillis()
        val weddingDateInMillis = weddingDate.toLongDate()
        val diffInMillis = weddingDateInMillis - currentDate
        return (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
    }

    fun getCountTillWeddingDayInString(weddingDate: String): String {
        val count = getCountTillWeddingDay(weddingDate)
        return if (count > 0) {
            "Faltam $count dias para o seu casamento!"
        } else {
            "Seu casamento já ocorreu! Parabéns!"
        }
    }

    fun getTasksByUserId(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getTasksByUserId(uid).collect { tasksList ->
                _tasks.value = tasksList
            }
        }
    }

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if(task.title.isBlank() || task.description.isBlank()) {
                _taskInsertState.value = TaskInsertState.Error("Preencha todos os campos!")
            } else {
                try {
                    _taskInsertState.value = TaskInsertState.Loading

                    // Inserir a tarefa no banco de dados
                    taskRepository.insertTask(task)
                    _taskInsertState.value = TaskInsertState.Success
                } catch (e: Exception) {
                    _taskInsertState.value = TaskInsertState.Error("Erro ao criar tarefa!")
                }
            }
        }
    }

    fun onCompleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTaskStatus(task)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
        }
    }

    fun clearTaskInsertState() {
        _taskInsertState.value = TaskInsertState.Idle
    }
}

sealed class TaskInsertState {
    object Idle : TaskInsertState()
    object Loading : TaskInsertState()
    object Success : TaskInsertState()
    data class Error(val message: String) : TaskInsertState()
}