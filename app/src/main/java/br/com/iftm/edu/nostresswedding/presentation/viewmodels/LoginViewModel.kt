package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
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
    private val userRepository: UserRepository,
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
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            try {
                val firebaseUser = loginRepository.signInWithEmailAndPassword(
                    email = _email.value,
                    password = _password.value
                )

                firebaseUser?.let { user ->
                    // Busca o usuário do Firestore
                    userRepository.getUserFromFirestore(
                        uid = user.uid,
                        onSuccess = { userEntity ->
                            // Salva o usuário no banco de dados local
                            viewModelScope.launch(Dispatchers.IO) {
                                userRepository.saveOrUpdateUserInRoom(userEntity)

                                // Atualiza o estado com o usuário logado
                                _uiState.value = LoginUiState.Success(userEntity)
                            }
                        },
                        onFailure = { exception ->
                            _uiState.value = LoginUiState.Error("Erro ao buscar dados do usuário no Firestore: ${exception.message}")
                        }
                    )
                } ?: run {
                    _uiState.value = LoginUiState.Error("Credenciais inválidas ou usuário não encontrado no Firebase.")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("Erro inesperado no login: ${e.message}")
            }
        }
    }
}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: UserEntity?) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}