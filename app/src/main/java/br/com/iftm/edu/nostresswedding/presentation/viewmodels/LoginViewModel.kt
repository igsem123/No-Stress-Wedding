package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.mappers.toUserDto
import br.com.iftm.edu.nostresswedding.data.mappers.toUserEntity
import br.com.iftm.edu.nostresswedding.data.repository.LoginRepository
import br.com.iftm.edu.nostresswedding.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel para gerenciar o estado do login
 * @param loginRepository Repositório para autenticação de login
 * @param userRepository Repositório para gerenciamento de usuários
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    // StateFlow para gerenciar o estado
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState>
        get() = _uiState

    // StateFlow para gerenciar o estado do email e senha
    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String>
        get() = _password

    // Função para atualizar o email
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Função para atualizar a senha
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    // Função para fazer login
    fun login() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                loginRepository.signInWithEmailAndUser(
                    email = _email.value,
                    password = _password.value,
                    onSuccess = { uid, user ->
                        viewModelScope.launch {
                            try {
                                val userEntity = user.toUserEntity()
                                withContext(Dispatchers.IO) {
                                    userRepository.saveOrUpdateUserInRoom(userEntity)
                                }
                                _uiState.value = LoginUiState.Success(uid)
                            } catch (e: Exception) {
                                _uiState.value = LoginUiState.Error("Erro ao salvar usuário: ${e.message}")
                            }
                        }
                    },
                    onFailure = { exception ->
                        _uiState.value = LoginUiState.Error(exception.message ?: "Erro desconhecido")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Erro inesperado: ${e.message}")
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val userId: String? = null) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}