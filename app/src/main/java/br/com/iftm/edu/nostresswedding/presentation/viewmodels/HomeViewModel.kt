package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.core.utils.toLongDate
import br.com.iftm.edu.nostresswedding.data.local.entity.TaskEntity
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.data.repository.LoginRepository
import br.com.iftm.edu.nostresswedding.data.repository.TaskRepository
import br.com.iftm.edu.nostresswedding.data.repository.UserRepository
import br.com.iftm.edu.nostresswedding.presentation.states.HomeUiState
import br.com.iftm.edu.nostresswedding.presentation.states.TaskCreationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun getUserDataFromRoom(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(user = userRepository.getUserFromRoom(uid))

            if(uiState.value.user == null) {
                // Se o usuário não estiver no banco de dados, busca do Firebase
                userRepository.getUserFromFirestore(
                    uid = uid,
                    onSuccess = { user ->
                        // Atualiza o estado com os dados do usuário obtidos
                        _uiState.value = _uiState.value.copy(user = user)

                        // Insere o usuário no banco de dados local
                        viewModelScope.launch(Dispatchers.IO) {
                            userRepository.saveOrUpdateUserInRoom(user)
                        }
                    },
                    onFailure = {
                        _uiState.value = _uiState.value.copy(error("Erro ao obter dados do usuário: ${it.message}"))
                    }
                )

            }

            getTasksByUserId(uid) // Carrega as tarefas do usuário ao obter os dados do usuário
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
                _uiState.value = _uiState.value.copy(tasks = tasksList.toList())
            }
        }
    }

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if(task.title.isBlank() || task.description.isBlank()) {
                _uiState.value = _uiState.value.copy(taskCreationState = TaskCreationState.Error("Preencha todos os campos!"))
            } else {
                try {
                    _uiState.value = _uiState.value.copy(taskCreationState = TaskCreationState.Loading)

                    // Inserir a tarefa no banco de dados
                    taskRepository.insertTask(task)

                    // Simulo um atraso para simular a inserção
                    delay(2000)

                    // Atualizar o estado para sucesso
                    _uiState.value = _uiState.value.copy(taskCreationState = TaskCreationState.Success)
                } catch (_: Exception) {
                    _uiState.value = _uiState.value.copy(taskCreationState = TaskCreationState.Error("Erro ao criar tarefa!"))
                }
            }
        }
    }

    fun onCompleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            taskRepository.completeTask(updatedTask)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
            getTasksByUserId(task.userId) // Atualiza a lista após deletar a tarefa
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
            getTasksByUserId(task.userId) // Atualiza a lista após atualizar a tarefa
        }
    }

    fun clearTaskInsertState() {
        _uiState.value = _uiState.value.copy(taskCreationState = TaskCreationState.Idle)
    }
}