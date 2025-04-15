package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para gerenciar o estado do registro
 * @param repository Repositório para autenticação de registro
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {

    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    fun onFieldChange(field: String, value: String) {
        _uiState.value = when (field) {
            "username" -> _uiState.value.copy(username = value)
            "password" -> _uiState.value.copy(password = value)
            "confirmPassword" -> _uiState.value.copy(confirmPassword = value)
            "name" -> _uiState.value.copy(name = value)
            "weddingDate" -> _uiState.value.copy(weddingDate = value)
            "weddingBudget" -> _uiState.value.copy(weddingBudget = value)
            else -> _uiState.value
        }
    }

    fun isPasswordEqual(): Boolean {
        return _uiState.value.password == _uiState.value.confirmPassword
    }

    fun registerUser() {
        with(_uiState.value) {
            if (username.isBlank() || password.isBlank() || name.isBlank() ||
                weddingDate.isBlank() || weddingBudget.isBlank()
            ) {
                _uiState.value = _uiState.value.copy(error = "Preencha todos os campos corretamente!")
                return
            }

            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.createUserInFirebase(username, password, { uid ->
                val userEntity = UserEntity(
                    uid = uid,
                    email = username,
                    name = name,
                    weddingDate = weddingDate,
                    weddingBudget = weddingBudget
                )

                repository.saveUserInFirestore(uid, userEntity, {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveUserInRoom(userEntity)
                        _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                    }
                    clearState()
                }, { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = exception.message)
                })
            }, { exception ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = exception.message)
            })
        }
    }

    fun clearState() {
        _uiState.value = RegisterUiState()
    }
}

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val weddingDate: String = "",
    val weddingBudget: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
